package com.opensource.weathercloset.record.controller;

import com.opensource.weathercloset.common.dto.BasicResponse;
import com.opensource.weathercloset.record.service.RecordSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/search")
@RequiredArgsConstructor
@Tag(name = "search", description = "기록 검색 API")
public class RecordSearchController {

    private final RecordSearchService recordSearchService;
    private BasicResponse basicResponse = new BasicResponse();

    @GetMapping()
    @Operation(summary = "기록 기온 범위 조회", description = "기온 범위에 해당하는 기록을 조회합니다")
    public ResponseEntity<BasicResponse> searchRecords (@RequestParam double minTemperature,
                                                        @RequestParam double maxTemperature) {
        return basicResponse.ok(
                recordSearchService.searchRecords(minTemperature, maxTemperature)
        );
    }

}
