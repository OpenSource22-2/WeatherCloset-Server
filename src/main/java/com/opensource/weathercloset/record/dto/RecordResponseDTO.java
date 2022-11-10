package com.opensource.weathercloset.record.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opensource.weathercloset.record.domain.Record;
import com.opensource.weathercloset.record.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@Builder(access = PRIVATE)
public class RecordResponseDTO {

    private String imageUrl;
    private int temperature;
    private int stars;
    private String comment;
    private boolean heart;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            locale = "Asia/Seoul"
    )
    private LocalDateTime createdAt;

    public static RecordResponseDTO from (Record record) {
        return RecordResponseDTO.builder()
                .imageUrl(record.getImageUrl())
                .temperature(record.getTemperature())
                .stars(record.getStars())
                .comment(record.getComment())
                .heart(record.isHeart())
                .build();
    }
}
