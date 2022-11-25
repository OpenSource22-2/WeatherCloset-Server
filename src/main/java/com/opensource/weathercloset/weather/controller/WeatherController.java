package com.opensource.weathercloset.weather.controller;

import com.opensource.weathercloset.weather.domain.Weather;
import com.opensource.weathercloset.weather.repository.WeatherRepository;
import com.opensource.weathercloset.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping(value = "/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final WeatherRepository weatherRepository;

    @Value("${app.weatherApiToken}")
    private String WEATHER_API_TOKEN;

    @PostMapping("/api/parse")
    @ResponseStatus(OK)
    public ResponseEntity<Weather> addWeather() {
        try {
                StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/AsosDalyInfoService/getWthrDataList"); /*URL*/
                urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + WEATHER_API_TOKEN); /*Service Key*/
                urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default : XML*/
                urlBuilder.append("&" + URLEncoder.encode("dataCd", "UTF-8") + "=" + URLEncoder.encode("ASOS", "UTF-8")); /*자료 분류 코드(ASOS)*/
                urlBuilder.append("&" + URLEncoder.encode("dateCd", "UTF-8") + "=" + URLEncoder.encode("DAY", "UTF-8")); /*날짜 분류 코드(DAY)*/
                urlBuilder.append("&" + URLEncoder.encode("startDt", "UTF-8") + "=" + URLEncoder.encode("20221124", "UTF-8")); /*조회 기간 시작일(YYYYMMDD)*/
                urlBuilder.append("&" + URLEncoder.encode("endDt", "UTF-8") + "=" + URLEncoder.encode("20221124", "UTF-8")); /*조회 기간 종료일(YYYYMMDD) (전일(D-1)까지 제공)*/
                urlBuilder.append("&" + URLEncoder.encode("stnIds", "UTF-8") + "=" + URLEncoder.encode("108", "UTF-8")); /*종관기상관측 지점 번호 (활용가이드 하단 첨부 참조)*/
                URL url = new URL(urlBuilder.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");

                System.out.println("Response code: " + conn.getResponseCode());
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

                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(result);

                JSONObject parse_response = (JSONObject) obj.get("response");
                JSONObject parse_body = (JSONObject) parse_response.get("body");
                JSONObject parse_items = (JSONObject) parse_body.get("items");

                JSONArray parse_item = (JSONArray) parse_items.get("item");

                JSONObject weather;

                for (int i = 0; i < parse_item.size(); i++) {
                    weather = (JSONObject) parse_item.get(i);

                    // date 형으로 바꾸기 전 string 형
                    String tm = (String) weather.get("tm");

                    // float 형으로 바꾸기 전 string 형
                    String strAvgTa = (String) weather.get("avgTa");
                    String strMinTa = (String) weather.get("minTa");
                    String strMaxTa = (String) weather.get("maxTa");
                    String strSnow = (String) weather.get("ddMefs");
                    String strRain = (String) weather.get("sumRn");
                    String strCloud = (String) weather.get("avgTca");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
                    LocalDate date = LocalDate.parse(tm, formatter);

                    float avgTa = Float.parseFloat(strAvgTa);
                    float minTa = Float.parseFloat(strMinTa);
                    float maxTa = Float.parseFloat(strMaxTa);

                    float snow;
                    if (strSnow.isEmpty())
                        snow = 0.0f;
                    else
                        snow = Float.parseFloat(strSnow);

                    float rain;
                    if (strRain.isEmpty())
                        rain = 0.0f;
                    else
                        rain = Float.parseFloat(strRain);

                    float cloud;
                    if (strCloud.isEmpty())
                        cloud = 0.0f;
                    else
                        cloud = Float.parseFloat(strCloud);

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

                    Weather createWeather = Weather.builder()
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
                            weatherService.addWeather(createWeather)
                    );
                }
            rd.close();
            conn.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}