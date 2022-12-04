package com.opensource.weathercloset.record.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecordUpdateRequestDTO {

    private String imageUrl;
    private int stars;
    private String comment;
    private boolean heart;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy. MM. dd",
            locale = "Asia/Seoul"
    )
    private LocalDate recordDate;
    @Size(min = 1, max = 3)
    private Set<Long> tagIds;

}
