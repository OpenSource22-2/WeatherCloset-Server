package com.opensource.weathercloset.record.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecordUpdateRequestDTO {

    private int stars;
    private String comment;
    private boolean heart;

}
