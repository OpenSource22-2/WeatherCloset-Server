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

    public static RecordResponseDTO from (Record record) {
        return RecordResponseDTO.builder()
                .imageUrl(record.getImageUrl())
                .stars(record.getStars())
                .comment(record.getComment())
                .heart(record.isHeart())
                .build();
    }
}
