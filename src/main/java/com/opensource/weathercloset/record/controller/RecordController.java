package com.opensource.weathercloset.record.controller;

import com.opensource.weathercloset.common.dto.BasicResponse;
import com.opensource.weathercloset.record.dto.HeartUpdateRequestDTO;
import com.opensource.weathercloset.record.dto.RecordRequestDTO;
import com.opensource.weathercloset.record.dto.RecordUpdateRequestDTO;
import com.opensource.weathercloset.record.service.RecordService;
import com.opensource.weathercloset.tag.domain.Tag;
import com.opensource.weathercloset.tag.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "record", description = "저장 API")
public class RecordController {

    private final RecordService recordService;
    private final TagService tagService;
    private final BasicResponse basicResponse = new BasicResponse();

    @GetMapping("/{recordId}")
    @Operation(summary = "기록 단건 조회", description = "기록을 단건 조회합니다")
    public ResponseEntity<BasicResponse> getRecord(@PathVariable("recordId") Long recordId) {
        return basicResponse.ok(
                recordService.getRecord(recordId)
        );
    }

    @PostMapping("/{memberId}")
    @Operation(summary = "기록 등록", description = "기록을 신규 등록합니다")
    public ResponseEntity<BasicResponse> addRecord(@PathVariable("memberId") Long memberId,
                                                   @RequestBody RecordRequestDTO requestDTO) {
        String imageUrl = requestDTO.getImageUrl();
        int stars = requestDTO.getStars();
        String comment = requestDTO.getComment();
        boolean heart = requestDTO.isHeart();
        LocalDate recordDate = requestDTO.getDate();
        Set<Tag> tags = getTags(requestDTO.getTagIds());

        return basicResponse.ok(
                recordService.addRecord(memberId, imageUrl, stars, comment, heart, recordDate, tags)
        );
    }

    @PutMapping("/{memberId}/{recordId}")
    @Operation(summary = "기록 수정", description = "이미지, 별점, 한 줄 기록, 좋아요 여부, 날짜를 수정합니다")
    public ResponseEntity<BasicResponse> updateRecord(@PathVariable("memberId") Long memberId,
                                                      @PathVariable("recordId") Long recordId, @RequestBody RecordUpdateRequestDTO requestDTO) {
        String imageUrl = requestDTO.getImageUrl();
        int stars = requestDTO.getStars();
        String comment = requestDTO.getComment();
        boolean heart = requestDTO.isHeart();
        LocalDate recordDate = requestDTO.getDate();
        Set<Tag> tags = getTags(requestDTO.getTagIds());

        recordService.updateRecord(memberId, recordId, imageUrl, stars, comment, heart, recordDate, tags);
        return basicResponse.noContent();
    }

    @PutMapping("/like/{memberId}/{recordId}")
    @Operation(summary = "좋아요 수정", description = "좋아요 여부를 수정합니다")
    public ResponseEntity<BasicResponse> updateHeart(@PathVariable("memberId") Long memberId,
                                                     @PathVariable("recordId") Long recordId,
                                                     @RequestBody HeartUpdateRequestDTO requestDTO) {
        boolean heart = requestDTO.isHeart();

        recordService.updateHeart(memberId, recordId, heart);
        return basicResponse.noContent();
    }

    @DeleteMapping("/{recordId}")
    @Operation(summary = "기록 삭제", description = "기록을 삭제합니다")
    public ResponseEntity<BasicResponse> deleteRecord(@PathVariable Long recordId) {
        recordService.deleteRecord(recordId);
        return basicResponse.noContent();
    }

    private Set<Tag> getTags(Set<Long> tagIds) {
        return tagService.getTagsByIds(tagIds);
    }

}
