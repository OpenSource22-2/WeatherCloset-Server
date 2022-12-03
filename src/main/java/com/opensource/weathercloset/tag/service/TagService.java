package com.opensource.weathercloset.tag.service;

import com.opensource.weathercloset.common.exception.EntityNotFoundException;
import com.opensource.weathercloset.common.exception.ErrorCode;
import com.opensource.weathercloset.tag.domain.Tag;
import com.opensource.weathercloset.tag.dto.TagResponseDTO;
import com.opensource.weathercloset.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagResponseDTO> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(TagResponseDTO::from)
                .collect(Collectors.toList());
    }

    public Set<Tag> getTagsByIds(Set<Long> ids) {
        return ids.stream()
                .map(this::getTagById)
                .collect(Collectors.toSet());
    }

    private Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.TAG_NOT_FOUND));
    }

}
