package com.opensource.weathercloset.record.service;

import com.opensource.weathercloset.record.domain.Record;
import com.opensource.weathercloset.record.dto.PostRecordRequest;
import com.opensource.weathercloset.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public Record postRecord(PostRecordRequest record) {
        Record recordToPost = new Record();

    }
}
