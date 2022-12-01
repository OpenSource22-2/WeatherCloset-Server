package com.opensource.weathercloset.record.service;

import com.opensource.weathercloset.common.exception.EntityNotFoundException;
import com.opensource.weathercloset.common.exception.ErrorCode;
import com.opensource.weathercloset.member.domain.Member;
import com.opensource.weathercloset.member.repository.MemberRepository;
import com.opensource.weathercloset.record.domain.Record;
import com.opensource.weathercloset.record.dto.RecordResponseDTO;
import com.opensource.weathercloset.record.repository.RecordRepository;
import com.opensource.weathercloset.weather.domain.Weather;
import com.opensource.weathercloset.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final MemberRepository memberRepository;
    private final WeatherRepository weatherRepository;

    public List<RecordResponseDTO> getRecords(Long memberId) {
        Member member = getMember(memberId);
        return recordRepository.findAllByMember(member).stream()
                .map(RecordResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public RecordResponseDTO addRecord(Long memberId, String imageUrl, int stars, String comment, boolean heart, LocalDate recordDate) {
        Member member = getMember(memberId);
        LocalDate date = recordDate;
        Optional<Weather> optWeather = weatherRepository.findByDate(date);
        Weather weather;
        if (optWeather.isPresent())     // 과거
            weather = optWeather.get();
        else {                          // 오늘
            weather = Weather.builder()
                    .avgTa(99.0)
                    .minTa(0.0)
                    .maxTa(0.0)
                    .snow(0.0)
                    .rain(0.0)
                    .cloud(0.0)
                    .date(recordDate)
                    .iconType(-1)
                    .build();
        }

        Record record = Record.builder()
                .member(member)
                .imageUrl(imageUrl)
                .stars(stars)
                .comment(comment)
                .heart(heart)
                .weather(weather)
                .recordDate(recordDate)
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
    public void updateHeart(Long recordId, boolean heart) {
        Record record = getRecord(recordId);
        record.setHeart(heart);
        recordRepository.save(record);
    }

    @Transactional
    public void deleteRecord(Long recordId) {
        Record record = getRecord(recordId);
        recordRepository.delete(record);
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Record getRecord(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.RECORD_NOT_FOUND));
    }
}

