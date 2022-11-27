package com.opensource.weathercloset.record.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opensource.weathercloset.record.domain.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
            pattern = "yyyy-MM-dd HH:mm:ss",
            locale = "Asia/Seoul"
    )
    private final LocalDateTime createdAt;

    private final double temperature;
    private final String icon;

    public static RecordResponseDTO from (Record record) {
        return RecordResponseDTO.builder()
                .id(record.getId())
                .username(record.getMember().getNickname())
                .imageUrl(record.getImageUrl())
                .stars(record.getStars())
                .comment(record.getComment())
                .heart(record.isHeart())
                .createdAt(record.getCreatedAt())
                .temperature(record.getWeather().getAvgTa())
//                .icon(record.getWeather().getIconType())
                .build();
    }
}
