package com.opensource.weathercloset.weather.domain;

import com.opensource.weathercloset.common.domain.DateTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "weather_id", nullable = false)
    private Long id;

    private LocalDate date;
    @Column(name = "avg_ta", nullable = false)
    private float avgTa;

    @Column(name = "min_ta", nullable = false)
    private float minTa;

    @Column(name = "max_ta", nullable = false)
    private float maxTa;

    @Column(nullable = false)
    private float snow;

    @Column(nullable = false)
    private float rain;

    @Column(nullable = false)
    private float cloud;

    @Column(nullable = false)
    private int icon_type;

    @Builder
    public Weather (float avgTa, float minTa, float maxTa, float snow, float rain, float cloud, int icon_type, LocalDate date) {
        this.avgTa = avgTa;
        this.minTa = minTa;
        this.maxTa = maxTa;
        this.snow = snow;
        this.rain = rain;
        this.cloud = cloud;
        this.icon_type = icon_type;
        this.date = date;
    }
}
