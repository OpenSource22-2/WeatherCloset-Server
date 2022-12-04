package com.opensource.weathercloset.tag.dto;

import com.opensource.weathercloset.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder(access = PRIVATE)
public class TagResponseDTO {

    private Long id;
    private String name;

    public static TagResponseDTO from(Tag tag) {
        return TagResponseDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}