package com.opensource.weathercloset.record.domain;

import com.opensource.weathercloset.common.domain.DateTimeEntity;
import com.opensource.weathercloset.heart.domain.Heart;
import com.opensource.weathercloset.member.domain.Member;
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

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;

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

    @Column(name = "date", nullable = false)
    private LocalDate recordDate;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "weather_id")
    private Weather weather;

    @OneToMany(mappedBy = "record", cascade = REMOVE, orphanRemoval = true, fetch = EAGER)
    private Set<Heart> hearts = new HashSet<>();

    @Builder
    public Record(Member member, Weather weather, String imageUrl, int stars, String comment, boolean heart, LocalDate recordDate) {
        this.weather = weather;
        this.member = member;
        this.imageUrl = imageUrl;
        this.stars = stars;
        this.comment = comment;
        this.recordDate = recordDate;
    }

    public void update(String imageUrl, int stars, String comment, LocalDate recordDate) {
        this.imageUrl = imageUrl;
        this.stars = stars;
        this.comment = comment;
        this.recordDate = recordDate;
    }

    public boolean didHeart(){
        return hearts.stream()
                .anyMatch(heart -> heart.didHeart(this.getId()));
    }

}
