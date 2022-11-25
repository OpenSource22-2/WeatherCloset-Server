package com.opensource.weathercloset.weather.controller;

import com.opensource.weathercloset.weather.domain.Weather;
import com.opensource.weathercloset.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.net.URLEncoder.encode;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping(value = "/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @Value("${app.weatherApiToken}")
    private String WEATHER_API_TOKEN;
    private static final String WEATHER_OPENAPI_URL = "http://apis.data.go.kr/1360000/AsosDalyInfoService/getWthrDataList";

    @PostMapping("/api/parse")
    @ResponseStatus(OK)
    public ResponseEntity<Weather> addWeather() {
        try {
            String result = callWeatherApi();
            JSONArray parse_item = getJsonArray(result);

            JSONObject jsonWeather;

                for (int i = 0; i < parse_item.size(); i++) {
                    jsonWeather = (JSONObject) parse_item.get(i);

                    // date 형으로 바꾸기 전 string 형
                    String tm = (String) jsonWeather.get("tm");

                    // float 형으로 바꾸기 전 string 형
                    String strAvgTa = (String) jsonWeather.get("avgTa");
                    String strMinTa = (String) jsonWeather.get("minTa");
                    String strMaxTa = (String) jsonWeather.get("maxTa");
                    String strSnow = (String) jsonWeather.get("ddMefs");
                    String strRain = (String) jsonWeather.get("sumRn");
                    String strCloud = (String) jsonWeather.get("avgTca");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
                    LocalDate date = LocalDate.parse(tm, formatter);

                    float avgTa = stringToFloat(strAvgTa);
                    float minTa = stringToFloat(strMinTa);
                    float maxTa = stringToFloat(strMaxTa);
                    float snow = stringToFloat(strSnow);
                    float rain = stringToFloat(strRain);
                    float cloud = stringToFloat(strCloud);
                    int icon_type = getIconType(snow, rain, cloud);

                    Weather weather = Weather.builder()
                            .avgTa(avgTa)
                            .minTa(minTa)
                            .maxTa(maxTa)
                            .snow(snow)
                            .rain(rain)
                            .cloud(cloud)
                            .date(date)
                            .icon_type(icon_type)
                            .build();

                    return ResponseEntity.ok(
                            weatherService.addWeather(weather)
                    );
                }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String callWeatherApi() throws IOException {
        StringBuilder urlBuilder = new StringBuilder(WEATHER_OPENAPI_URL)
                .append("?" + encode("serviceKey", "UTF-8") + "=" + WEATHER_API_TOKEN) /*Service Key*/
                .append("&" + encode("dataType", "UTF-8") + "=" + encode("JSON", "UTF-8")) /*요청자료형식(XML/JSON) Default : XML*/
                .append("&" + encode("dataCd", "UTF-8") + "=" + encode("ASOS", "UTF-8")) /*자료 분류 코드(ASOS)*/
                .append("&" + encode("dateCd", "UTF-8") + "=" + encode("DAY", "UTF-8")) /*날짜 분류 코드(DAY)*/
                .append("&" + encode("startDt", "UTF-8") + "=" + encode("20221124", "UTF-8")) /*조회 기간 시작일(YYYYMMDD)*/
                .append("&" + encode("endDt", "UTF-8") + "=" + encode("20221124", "UTF-8")) /*조회 기간 종료일(YYYYMMDD) (전일(D-1)까지 제공)*/
                .append("&" + encode("stnIds", "UTF-8") + "=" + encode("108", "UTF-8")); /*종관기상관측 지점 번호 (활용가이드 하단 첨부 참조)*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        String line;
        String result = "";
        while ((line = rd.readLine()) != null) {
            result = result.concat(line);
        }
        rd.close();
        conn.disconnect();

        return result;
    }

    private JSONArray getJsonArray(String result) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(result);

        JSONObject parse_response = (JSONObject) obj.get("response");
        JSONObject parse_body = (JSONObject) parse_response.get("body");
        JSONObject parse_items = (JSONObject) parse_body.get("items");

        JSONArray parse_item = (JSONArray) parse_items.get("item");
        return parse_item;
    }

    private float stringToFloat(String stringItem) {
        float floatItem;

        if (stringItem.isEmpty())
            floatItem = 0.0f;
        else
            floatItem = Float.parseFloat(stringItem);

        return floatItem;
    }

    private static int getIconType(float snow, float rain, float cloud) {
        int icon_type;

        if (snow > 0)
            icon_type = 1; // 눈
        else if (snow == 0 && rain > 0)
            icon_type = 2; // 비
        else if (snow == 0 && rain == 0 && cloud >= 8.0)
            icon_type = 3; // 흐림
        else if (snow == 0 && rain == 0 && cloud >= 3.0)
            icon_type = 4; // 조금 흐림
        else
            icon_type = 5; // 맑음

        return icon_type;
    }

}