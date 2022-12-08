package com.opensource.weathercloset.member.controller;

import com.opensource.weathercloset.common.dto.BasicResponse;
import com.opensource.weathercloset.record.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "member", description = "사용자 기록 조회 API")
public class MemberController {

    private final RecordService recordService;
    private final BasicResponse basicResponse = new BasicResponse();

    @GetMapping("/{memberId}")
    @Operation(summary = "사용자 기록 최신 순 조회", description = "사용자의 기록을 최신 순으로 조회합니다")
    public ResponseEntity<BasicResponse> getRecords(@PathVariable("memberId") Long memberId) {
        return basicResponse.ok(
                recordService.getRecords(memberId)
        );
    }

    @GetMapping("/temperature/{memberId}")
    @Operation(summary = "기온 기반 사용자 기록 조회", description = "사용자의 온도 +-5인 기록을 조회합니다")
    public ResponseEntity<BasicResponse> getRecordsByTemperature(@PathVariable("memberId") Long memberId, @RequestParam double temperature) {
        return basicResponse.ok(
                recordService.getRecordsByTemperature(memberId, temperature)
        );
    }

    @GetMapping("/heart/{memberId}")
    @Operation(summary = "사용자가 좋아요 누른 기록 조회", description = "사용자가 좋아요 누른 기록을 조회합니다")
    public ResponseEntity<BasicResponse> getHomeRecords(@PathVariable("memberId") Long memberId) {
        return basicResponse.ok(
                recordService.getRecordsByHeart(memberId)
        );
    }

}
