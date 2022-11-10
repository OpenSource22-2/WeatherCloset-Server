package com.opensource.weathercloset.record.service;

import com.opensource.weathercloset.record.domain.Record;
import com.opensource.weathercloset.record.dto.RecordRequestDTO;
import com.opensource.weathercloset.record.dto.RecordResponseDTO;
import com.opensource.weathercloset.record.dto.RecordUpdateRequestDTO;
import com.opensource.weathercloset.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    @Transactional
    public RecordResponseDTO addRecord(String imageUrl, int temperature,
                               int stars, String comment, boolean heart) {
        Record record = Record.builder()
                .imageUrl(imageUrl)
                .temperature(temperature)
                .stars(stars)
                .comment(comment)
                .heart(heart)
                .build();
        Record saved = recordRepository.save(record);
        return RecordResponseDTO.from(saved);
    }

    @Transactional
    public void updateRecord(Long recordId, int stars, String comment, boolean heart) {
        Record record = getRecord(recordId);
        record.update(stars, comment, heart);
        recordRepository.save(record);
    }

    @Transactional
    public void deleteRecord(Long recordId) {
        Record record = getRecord(recordId);
        recordRepository.delete(record);
    }

    private Record getRecord(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(RuntimeException::new);  // TODO : Custom Exception
    }
}

