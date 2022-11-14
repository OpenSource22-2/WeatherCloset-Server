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

    private final int temperature = 0;  // 클라이언트와 테스트를 위해 임시로 둠

    public static RecordResponseDTO from (Record record) {
        return RecordResponseDTO.builder()
                .imageUrl(record.getImageUrl())
                .stars(record.getStars())
                .comment(record.getComment())
                .heart(record.isHeart())
                .createdAt(record.getCreatedAt())
                .build();
    }
}
