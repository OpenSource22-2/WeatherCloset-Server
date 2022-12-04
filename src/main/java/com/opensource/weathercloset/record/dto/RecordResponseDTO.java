package com.opensource.weathercloset.record.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opensource.weathercloset.record.domain.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@Builder(access = PRIVATE)
public class RecordResponseDTO {

    private final Long id;
    private final String username;
    private final String imageUrl;
    private final int stars;
    private final String comment;
    private final boolean heart;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy. MM. dd",
            locale = "Asia/Seoul"
    )
    private final LocalDate date;

    private final double temperature;
    private final int icon;
    private final Set<String> tags;

    public static RecordResponseDTO from (Record record) {
        return RecordResponseDTO.builder()
                .id(record.getId())
                .username(record.getMember().getNickname())
                .imageUrl(record.getImageUrl())
                .stars(record.getStars())
                .comment(record.getComment())
                .heart(record.isHeart())
                .date(record.getDate())
                .temperature(record.getWeather().getAvgTa())
                .icon(record.getWeather().getIconType())
                .tags(record.getTags())
                .build();
    }
}
