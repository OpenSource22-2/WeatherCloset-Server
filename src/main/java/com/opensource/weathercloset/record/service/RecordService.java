package com.opensource.weathercloset.record.service;

import com.opensource.weathercloset.common.exception.EntityNotFoundException;
import com.opensource.weathercloset.common.exception.ErrorCode;
import com.opensource.weathercloset.heart.service.HeartService;
import com.opensource.weathercloset.member.domain.Member;
import com.opensource.weathercloset.member.repository.MemberRepository;
import com.opensource.weathercloset.record.domain.Record;
import com.opensource.weathercloset.record.dto.RecordResponseDTO;
import com.opensource.weathercloset.record.dto.RecordsResponseDTO;
import com.opensource.weathercloset.record.repository.RecordRepository;
import com.opensource.weathercloset.tag.domain.Tag;
import com.opensource.weathercloset.weather.domain.Weather;
import com.opensource.weathercloset.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final MemberRepository memberRepository;
    private final WeatherRepository weatherRepository;
    private final HeartService heartService;

    public List<RecordsResponseDTO> getRecords(Long memberId) {
        Member member = findMember(memberId);
        return recordRepository.findAllByMember(member).stream()
                .map(RecordsResponseDTO::from)
                .collect(Collectors.toList());
    }

    public RecordResponseDTO getRecord(Long recordId) {
        Record record = findRecord(recordId);
        return RecordResponseDTO.from(record);
    }

    public List<RecordsResponseDTO> getHomeRecords(Long memberId, double temperature) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        return recordRepository.findAllByMemberAndTemperature(memberId, temperature, Pageable.ofSize(8)).stream()
                .map(RecordsResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public RecordResponseDTO addRecord(Long memberId, String imageUrl, int stars, String comment, boolean heart, LocalDate recordDate, Set<Tag> tags) {
        Member member = findMember(memberId);
        LocalDate date = recordDate;
        Optional<Weather> optWeather = weatherRepository.findByDate(date);
        Weather weather;
        if (optWeather.isPresent())     // 과거
            weather = optWeather.get();
        else {                          // 오늘
            weather = dummyWeather(recordDate);
        }

        Record record = Record.builder()
                .member(member)
                .imageUrl(imageUrl)
                .stars(stars)
                .comment(comment)
                .weather(weather)
                .date(recordDate)
                .build();
        record.setTags(tags);
        if(heart)
            heartService.heart(member, record);

        Record saved = recordRepository.save(record);
        return RecordResponseDTO.from(saved);
    }

    @Transactional
    public void updateRecord(Long memberId, Long recordId, String imageUrl, int stars, String comment, boolean heart, LocalDate recordDate, Set<Tag> tags) {
        Member member = findMember(memberId);
        Record record = findRecord(recordId);
        record.update(imageUrl, stars, comment, recordDate, tags);
        if(heart)  // false -> true
            heartService.heart(member, record);
        else   // true -> false
            heartService.unheart(member, record);

        recordRepository.save(record);
    }
    @Transactional
    public void updateHeart(Long memberId, Long recordId, boolean heart) {
        Member member = findMember(memberId);
        Record record = findRecord(recordId);
        if(heart)   // false -> true
            heartService.heart(member, record);
        else   // true -> false
            heartService.unheart(member, record);

        recordRepository.save(record);
    }

    @Transactional
    public void deleteRecord(Long recordId) {
        Record record = findRecord(recordId);
        recordRepository.delete(record);
    }

    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Record findRecord(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.RECORD_NOT_FOUND));
    }

    private Weather dummyWeather(LocalDate recordDate) {
        return Weather.builder()
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

}

