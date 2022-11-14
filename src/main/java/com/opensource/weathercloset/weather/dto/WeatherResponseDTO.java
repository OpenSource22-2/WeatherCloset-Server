package com.opensource.weathercloset.weather.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opensource.weathercloset.record.domain.Record;
import com.opensource.weathercloset.record.dto.RecordResponseDTO;
import com.opensource.weathercloset.weather.domain.Weather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@Builder(access = PRIVATE)
public class WeatherResponseDTO {
    private final float avgTa;
    private final float minTa;
    private final float maxTa;
    private final float snow;
    private final float rain;
    private final float cloud;
    private final LocalDate date;

    public static WeatherResponseDTO from (Weather weather) {
        return WeatherResponseDTO.builder()
                .avgTa(weather.getAvgTa())
                .minTa(weather.getMinTa())
                .maxTa(weather.getMaxTa())
                .snow(weather.getSnow())
                .rain(weather.getRain())
                .cloud(weather.getCloud())
                .date(weather.getDate())
                .build();
    }
}