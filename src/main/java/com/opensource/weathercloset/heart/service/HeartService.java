package com.opensource.weathercloset.heart.service;

import com.opensource.weathercloset.common.exception.BusinessException;
import com.opensource.weathercloset.common.exception.EntityNotFoundException;
import com.opensource.weathercloset.common.exception.ErrorCode;
import com.opensource.weathercloset.heart.domain.Heart;
import com.opensource.weathercloset.heart.repository.HeartRepository;
import com.opensource.weathercloset.member.domain.Member;
import com.opensource.weathercloset.record.domain.Record;
import com.opensource.weathercloset.record.dto.RecordsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;

    public List<RecordsResponseDTO> getHeart(Member member) {
        return heartRepository.findAllByMember(member).stream()
                .map(RecordsResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void heart(Member member, Record record) {
        checkDuplicate(member, record);

        Heart heart = new Heart(member, record);
        heartRepository.save(heart);
    }

    @Transactional
    public void unheart(Member member, Record record) {
        Heart heart = findHeart(member, record);
        heartRepository.delete(heart);
    }

    private void checkDuplicate(Member member, Record record) {
        if (heartRepository.findByMemberAndRecord(member, record).isPresent()) {
            throw new BusinessException(ErrorCode.HEART_DUPLICATE);
        }
    }

    private Heart findHeart(Member member, Record record) {
        return heartRepository.findByMemberAndRecord(member, record)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.HEART_NOT_FOUND));
    }

}
