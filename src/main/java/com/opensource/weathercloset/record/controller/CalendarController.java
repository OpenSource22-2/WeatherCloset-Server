package com.opensource.weathercloset.record.controller;

import com.opensource.weathercloset.common.dto.BasicResponse;
import com.opensource.weathercloset.record.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@Tag(name = "calendar", description = "캘린더 API")
public class CalendarController {

    private final CalendarService calendarService;
    private BasicResponse basicResponse = new BasicResponse();


    @GetMapping("/{memberId}")
    @Operation(summary = "캘린더(사용자 월 기록 조회)", description = "사용자의 월 기록을 조회합니다")
    public ResponseEntity<BasicResponse> getCalendarInfo(@PathVariable("memberId") Long memberId,
                                                         @RequestParam int year, @RequestParam int month) {
        LocalDate localDate = LocalDate.of(year, month, 1);
        return basicResponse.ok(
                calendarService.getRecordsByMemberAndDate(memberId, localDate)
        );
    }


}