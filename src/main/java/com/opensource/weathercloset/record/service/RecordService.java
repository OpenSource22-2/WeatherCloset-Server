package com.opensource.weathercloset.record.service;

import com.opensource.weathercloset.member.domain.Member;
import com.opensource.weathercloset.member.repository.MemberRepository;
import com.opensource.weathercloset.record.domain.Record;
import com.opensource.weathercloset.record.dto.RecordResponseDTO;
import com.opensource.weathercloset.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final MemberRepository memberRepository;

    public List<RecordResponseDTO> getRecords(Long memberId, Pageable pageable) {
        Member member = getMember(memberId);
        return recordRepository.findAllByMember(member, pageable).stream()
                .map(RecordResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public RecordResponseDTO addRecord(Long memberId, String imageUrl, int stars, String comment, boolean heart) {
        Member member = getMember(memberId);
        Record record = Record.builder()
                .member(member)
                .imageUrl(imageUrl)
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
                .orElseThrow(RuntimeException::new);  // TODO : Custom Exception
    }

    private Record getRecord(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(RuntimeException::new);  // TODO : Custom Exception
    }
}

