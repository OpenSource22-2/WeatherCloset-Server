package com.opensource.weathercloset.tag.init;

import com.opensource.weathercloset.tag.domain.Tag;
import com.opensource.weathercloset.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class InitTag {

    private static final List<String> tagNames = List.of("더워요", "따뜻해요", "습해요", "일교차가 커요", "선선해요", "쌀쌀해요", "건조해요", "바람이 불어요", "햇빛이 강해요");
    private final TagRepository tagRepository;

    @PostConstruct
    public void init() {
        List<Tag> tags = tagNames.stream()
                .map(Tag::new)
                .collect(Collectors.toList());
        tagRepository.saveAll(tags);
    }

}