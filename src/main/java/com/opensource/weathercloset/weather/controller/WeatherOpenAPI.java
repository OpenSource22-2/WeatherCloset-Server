package com.opensource.weathercloset.weather.controller;

import com.opensource.weathercloset.weather.domain.Weather;
import com.opensource.weathercloset.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.net.URLEncoder.encode;


@EnableScheduling
@RestController
@RequestMapping(value = "/weather")
@RequiredArgsConstructor
@Tag(name = "weather", description = "날씨 정보 자동 저장 API")
public class WeatherOpenAPI {

    private final WeatherService weatherService;

    @Value("${app.weatherApiToken}")
    private String WEATHER_API_TOKEN;
    private static final String WEATHER_OPENAPI_URL = "http://apis.data.go.kr/1360000/AsosDalyInfoService/getWthrDataList";

    @Scheduled(cron="0 0 12 * * ?")
    @PostMapping("/api/parse")
    @Operation(summary = "날씨 API 자동 호출", description = "매일 오후 12시에 전날의 날씨 정보를 호출하여 DB에 저장합니다")
    public void addWeather() {
        try {
            String result = callWeatherApi();
            JSONArray parseItem = getJsonArray(result);

            JSONObject jsonWeather;


            for (int i = 0; i < parseItem.size(); i++) {
                jsonWeather = (JSONObject) parseItem.get(i);

                // date 형으로 바꾸기 전 string 형
                String tm = (String) jsonWeather.get("tm");

                // double 형으로 바꾸기 전 string 형
                String strAvgTa = (String) jsonWeather.get("avgTa");
                String strMinTa = (String) jsonWeather.get("minTa");
                String strMaxTa = (String) jsonWeather.get("maxTa");
                String strSnow = (String) jsonWeather.get("ddMefs");
                String strRain = (String) jsonWeather.get("sumRn");
                String strCloud = (String) jsonWeather.get("avgTca");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
                LocalDate date = LocalDate.parse(tm, formatter);

                double avgTa = stringToDouble(strAvgTa);
                double minTa = stringToDouble(strMinTa);
                double maxTa = stringToDouble(strMaxTa);
                double snow = stringToDouble(strSnow);
                double rain = stringToDouble(strRain);
                double cloud = stringToDouble(strCloud);
                int iconType = getIconType(snow, rain, cloud);

                Weather weather = Weather.builder()
                        .avgTa(avgTa)
                        .minTa(minTa)
                        .maxTa(maxTa)
                        .snow(snow)
                        .rain(rain)
                        .cloud(cloud)
                        .date(date)
                        .iconType(iconType)
                        .build();

                weatherService.addWeather(weather);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private String callWeatherApi() throws IOException {
        String strYesterday = getYesterday();

        StringBuilder urlBuilder = new StringBuilder(WEATHER_OPENAPI_URL)
                .append("?" + encode("serviceKey", "UTF-8") + "=" + WEATHER_API_TOKEN) /*Service Key*/
                .append("&" + encode("dataType", "UTF-8") + "=" + encode("JSON", "UTF-8")) /*요청자료형식(XML/JSON) Default : XML*/
                .append("&" + encode("dataCd", "UTF-8") + "=" + encode("ASOS", "UTF-8")) /*자료 분류 코드(ASOS)*/
                .append("&" + encode("dateCd", "UTF-8") + "=" + encode("DAY", "UTF-8")) /*날짜 분류 코드(DAY)*/
                .append("&" + encode("startDt", "UTF-8") + "=" + encode("20221030", "UTF-8")) /*조회 기간 시작일(YYYYMMDD)*/
                .append("&" + encode("endDt", "UTF-8") + "=" + encode("20221031", "UTF-8")) /*조회 기간 종료일(YYYYMMDD) (전일(D-1)까지 제공)*/
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

    private JSONArray getJsonArray(String result) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(result);

        JSONObject parseResponse = (JSONObject) obj.get("response");
        JSONObject parseBody = (JSONObject) parseResponse.get("body");
        JSONObject parseItems = (JSONObject) parseBody.get("items");

        JSONArray parseItem = (JSONArray) parseItems.get("item");

        return parseItem;
    }

    private double stringToDouble(String stringItem) {
        double doubleItem;

        if (stringItem.isEmpty())
            doubleItem = 0.0;
        else
            doubleItem = Double.parseDouble(stringItem);

        return doubleItem;
    }

    private int getIconType(double snow, double rain, double cloud) {
        int iconType;

        if (snow > 0)
            iconType = 1; // 눈
        else if (snow == 0 && rain > 0)
            iconType = 2; // 비
        else if (snow == 0 && rain == 0 && cloud >= 8.0)
            iconType = 3; // 흐림
        else if (snow == 0 && rain == 0 && cloud >= 3.0)
            iconType = 4; // 조금 흐림
        else
            iconType = 5; // 맑음

        return iconType;
    }

    private String getYesterday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = new GregorianCalendar();
        c1.add(Calendar.DATE, -1);

        return sdf.format(c1.getTime());
    }

}