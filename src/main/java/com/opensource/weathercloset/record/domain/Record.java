package com.opensource.weathercloset.record.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "record_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    private int temperature;

    @ColumnDefault("0")  // TODO : 프론트랑 상의
    private int satisfaction;

    @Column(length = 50)
    private String shortLog;  // comment

    @ColumnDefault("0")
    private int heart;

    @Enumerated(EnumType.STRING)
    private Tag feelingTemperature;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Builder
    public Record(Long id, String imageUrl, int temperature, int satisfaction, String shortLog, int heart, Tag feelingTemperature) {
        Assert.notNull(imageUrl, "imageUrl must not be null");
        this.id = id;
        this.imageUrl = imageUrl;
        this.temperature = temperature;
        this.satisfaction = satisfaction;
        this.shortLog = shortLog;
        this.heart = heart;
        this.feelingTemperature = feelingTemperature;
    }
}
