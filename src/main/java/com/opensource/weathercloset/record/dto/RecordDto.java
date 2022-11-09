package com.opensource.weathercloset.record.dto;

import com.opensource.weathercloset.record.Feeling_type;
import com.opensource.weathercloset.record.domain.Record;
import com.opensource.weathercloset.record.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@Builder(access = PRIVATE)
public class RecordDto {

    private String imageUrl;
    private int temperature;
    private int satisfaction;
    private String shortLog;
    private int heart;
    private Tag feelingTemperature;

    public RecordDto from (Record record) {
        return RecordDto.builder()
                .imageUrl(record.getImageUrl())
                .temperature(record.getTemperature())
                .satisfaction(record.getSatisfaction())
                .shortLog(record.getShortLog())
                .heart(record.getHeart())
                .feelingTemperature(record.getFeelingTemperature())
                .build();
    }
}
