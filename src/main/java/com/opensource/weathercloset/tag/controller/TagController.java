package com.opensource.weathercloset.tag.controller;

import com.opensource.weathercloset.tag.dto.TagResponseDTO;
import com.opensource.weathercloset.tag.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequiredArgsConstructor
@RequestMapping("tag")
@Tag(name = "tag", description = "태그 API")
public class TagController {

    private final TagService tagService;

    @GetMapping("all")
    @ResponseStatus(OK)
    @Operation(summary = "모든 태그 가져오기", description = "존재하는 모든 태그를 가져옵니다")
    public List<TagResponseDTO> getTags() {
        return tagService.getAllTags();
    }
}
