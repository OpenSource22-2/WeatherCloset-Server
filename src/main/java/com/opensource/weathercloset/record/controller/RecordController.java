package com.opensource.weathercloset.record.controller;

import com.opensource.weathercloset.record.service.RecordService;
import com.opensource.weathercloset.record.dto.PostRecordRequest;
import com.opensource.weathercloset.record.dto.RecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping("/post")
    public ResponseEntity<RecordDto> postRecord(@RequestBody PostRecordRequest postRecordRequest) {
        return ResponseEntity.ok(recordService.postRecord(postRecordRequest));
    }

}
