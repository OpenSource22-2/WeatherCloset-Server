package com.opensource.weathercloset.record.domain;

import com.opensource.weathercloset.calendar.domain.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "record_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    private int temperature;

    @ColumnDefault("0")  // TODO : 프론트랑 상의 -> star 개수를 필수로 할 것인지
    private int stars;

    @Column(length = 50)
    private String comment;

    private boolean heart;

    @Builder
    public Record(String imageUrl, int temperature, int stars, String comment, boolean heart) {
        this.imageUrl = imageUrl;
        this.temperature = temperature;
        this.stars = stars;
        this.comment = comment;
        this.heart = heart;
    }

    public void update(int stars, String comment, boolean heart) {
        this.stars = stars;
        this.comment = comment;
        this.heart = heart;
    }

}
