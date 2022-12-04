package com.opensource.weathercloset.record.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opensource.weathercloset.record.domain.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@Builder(access = PRIVATE)
public class RecordsResponseDTO {

    private final Long id;
    private final String username;
    private final String imageUrl;
    private final boolean heart;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy. MM. dd",
            locale = "Asia/Seoul"
    )
    private final LocalDate date;
    private final double temperature;

    public static RecordsResponseDTO from (Record record) {
        return RecordsResponseDTO.builder()
                .id(record.getId())
                .username(record.getMember().getNickname())
                .imageUrl(record.getImageUrl())
                .heart(record.didHeart())
                .date(record.getDate())
                .temperature(record.getWeather().getAvgTa())
                .build();
    }
}