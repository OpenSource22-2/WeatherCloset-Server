package com.opensource.weathercloset.record.controller;

import com.opensource.weathercloset.common.dto.BasicResponse;
import com.opensource.weathercloset.record.dto.HeartUpdateRequestDTO;
import com.opensource.weathercloset.record.dto.RecordRequestDTO;
import com.opensource.weathercloset.record.dto.RecordUpdateRequestDTO;
import com.opensource.weathercloset.record.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/record")
@RequiredArgsConstructor
@Tag(name = "record", description = "저장 API")
public class RecordController {

    private final RecordService recordService;
    private BasicResponse basicResponse = new BasicResponse();

    @GetMapping("/{memberId}")
    @ResponseStatus(OK)
    @Operation(summary = "사용자 기록 조회", description = "사용자의 기록을 조회합니다")
    public ResponseEntity<BasicResponse> getRecords(@PathVariable("memberId") Long memberId) {
        return basicResponse.ok(
                recordService.getRecords(memberId)
        );
    }

    @PostMapping("/{memberId}")
    @ResponseStatus(OK)
    @Operation(summary = "기록 등록", description = "기록을 신규 등록합니다")
    public ResponseEntity<BasicResponse> addRecord(@PathVariable("memberId") Long memberId, @RequestBody RecordRequestDTO requestDTO) {
        String imageUrl = requestDTO.getImageUrl();
        int stars = requestDTO.getStars();
        String comment = requestDTO.getComment();
        boolean heart = requestDTO.isHeart();
        LocalDate recordDate = requestDTO.getRecordDate();

        return basicResponse.ok(
                recordService.addRecord(memberId, imageUrl, stars, comment, heart, recordDate)
        );
    }

    @PutMapping("/{recordId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "기록 수정", description = "별점, 한 줄 기록, 좋아요 여부를 수정합니다")
    public ResponseEntity updateRecord(@PathVariable("recordId") Long recordId,
                                                      @RequestBody RecordUpdateRequestDTO requestDTO) {
        int stars = requestDTO.getStars();
        String comment = requestDTO.getComment();
        boolean heart = requestDTO.isHeart();

        recordService.updateRecord(recordId, stars, comment, heart);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/like/{recordId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "좋아요 수정", description = "좋아요 여부를 수정합니다")
    public ResponseEntity updateHeart(@PathVariable("recordId") Long recordId,
                                       @RequestBody HeartUpdateRequestDTO requestDTO) {
        boolean heart = requestDTO.isHeart();

        recordService.updateHeart(recordId, heart);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{recordId}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "기록 삭제", description = "기록을 삭제합니다")
    public ResponseEntity deleteRecord(@PathVariable Long recordId) {
        recordService.deleteRecord(recordId);
        return ResponseEntity.noContent().build();
    }

}
