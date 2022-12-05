package com.opensource.weathercloset.record.service;

import com.opensource.weathercloset.common.exception.EntityNotFoundException;
import com.opensource.weathercloset.common.exception.ErrorCode;
import com.opensource.weathercloset.member.repository.MemberRepository;
import com.opensource.weathercloset.record.dto.RecordsResponseDTO;
import com.opensource.weathercloset.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarService {

    private final RecordRepository recordRepository;
    private final MemberRepository memberRepository;

    public List<RecordsResponseDTO> getRecordsByMemberAndDate(Long memberId, LocalDate date) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        return recordRepository.findAllByMemberAndDate(memberId, date, Pageable.ofSize(31)).stream()
                .map(RecordsResponseDTO::from)
                .collect(Collectors.toList());
    }

}
