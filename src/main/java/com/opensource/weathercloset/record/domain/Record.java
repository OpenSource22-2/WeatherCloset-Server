package com.opensource.weathercloset.record.domain;

import com.opensource.weathercloset.common.domain.DateTimeEntity;
import com.opensource.weathercloset.member.domain.Member;
import com.opensource.weathercloset.tag.domain.RecordTag;
import com.opensource.weathercloset.tag.domain.Tag;
import com.opensource.weathercloset.weather.domain.Weather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Record extends DateTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "record_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @ColumnDefault("0")
    private int stars;

    @Column(length = 50)
    private String comment;

    private boolean heart;

    @Column(name = "date", nullable = false)
    private LocalDate recordDate;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = EAGER, cascade = PERSIST)
    @JoinColumn(name = "weather_id")
    private Weather weather;

    @OneToMany(mappedBy = "record", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private Set<RecordTag> tags = new HashSet<>();

    @Builder
    public Record(Member member, Weather weather, Set<RecordTag> tags, String imageUrl, int stars, String comment, boolean heart, LocalDate recordDate) {
        this.weather = weather;
        this.member = member;
        this.tags = tags;
        this.imageUrl = imageUrl;
        this.stars = stars;
        this.comment = comment;
        this.heart = heart;
        this.recordDate = recordDate;
    }

    public void update(String imageUrl, int stars, String comment, boolean heart, LocalDate recordDate, Set<Tag> tags) {
        this.imageUrl = imageUrl;
        this.stars = stars;
        this.comment = comment;
        this.heart = heart;
        this.recordDate = recordDate;
        setTags(tags);
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags.stream()
                .map(tag -> new RecordTag(this, tag))
                .collect(Collectors.toSet());
    }

    public Set<Tag> getTags() {
        return tags.stream()
                .map(RecordTag::getTag)
                .collect(Collectors.toSet());
    }

    public void setHeart(boolean heart) {
        this.heart = heart;
    }


}
