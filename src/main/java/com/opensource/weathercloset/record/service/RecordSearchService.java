package com.opensource.weathercloset.record.service;

import com.opensource.weathercloset.record.dto.RecordsResponseDTO;
import com.opensource.weathercloset.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RecordSearchService {

    private final RecordRepository recordRepository;

    public List<RecordsResponseDTO> searchRecords(double minTemperature, double maxTemperature) {
        return recordRepository.findAllByTemperatureBetween(minTemperature, maxTemperature, Pageable.ofSize(8)).stream()
                .map(RecordsResponseDTO::from)
                .collect(Collectors.toList());
    }

}
