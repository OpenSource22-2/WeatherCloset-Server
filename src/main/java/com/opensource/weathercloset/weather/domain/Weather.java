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
    @Column(nullable = false)
    private float avgTa;

    @Column(nullable = false)
    private float minTa;

    @Column(nullable = false)
    private float maxTa;

    private float snow;

    private float rain;

    private float cloud;

    @Builder
    public Weather (float avgTa, float minTa, float maxTa, float snow, float rain, float cloud, LocalDate date) {
        this.avgTa = avgTa;
        this.minTa = minTa;
        this.maxTa = maxTa;
        this.snow = snow;
        this.rain = rain;
        this.cloud = cloud;
        this.date = date;
    }
}
