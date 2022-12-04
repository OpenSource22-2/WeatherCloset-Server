package com.opensource.weathercloset.heart.controller;

import com.opensource.weathercloset.common.dto.BasicResponse;
import com.opensource.weathercloset.heart.service.HeartService;
import com.opensource.weathercloset.member.domain.Member;
import com.opensource.weathercloset.record.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/heart")
@Tag(name = "heart", description = "좋아요 API")
public class HeartController {

    private final RecordService recordService;
    private final HeartService heartService;
    private final BasicResponse basicResponse = new BasicResponse();

    @GetMapping("/{memberId}")
    @Operation(summary = "좋아요 기록 조회", description = "사용자가 좋아요 한 기록을 조회합니다")
    public ResponseEntity<BasicResponse> getHeart(@PathVariable("memberId") Long memberId) {
        Member member = recordService.findMember(memberId);
        return basicResponse.ok(
                heartService.getHeart(member)
        );
    }

}
