package com.opensource.weathercloset.weather.domain;
import com.opensource.weathercloset.common.domain.DateTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@NoArgsConstructor
public class Weather extends DateTimeEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "weather_id", nullable = false)
    private Long id;

    private LocalDate date;
    @Column(name = "avg_ta", nullable = false)
    private double avgTa;

    @Column(name = "min_ta", nullable = false)
    private double minTa;

    @Column(name = "max_ta", nullable = false)
    private double maxTa;

    @Column(nullable = false)
    private double snow;

    @Column(nullable = false)
    private double rain;

    @Column(nullable = false)
    private double cloud;

    @Column(nullable = false)
    private int iconType;

    @Builder
    public Weather (Long id, double avgTa, double minTa, double maxTa, double snow, double rain, double cloud, int iconType, LocalDate date) {
        this.id = id;
        this.avgTa = avgTa;
        this.minTa = minTa;
        this.maxTa = maxTa;
        this.snow = snow;
        this.rain = rain;
        this.cloud = cloud;
        this.iconType = iconType;
        this.date = date;
    }
}
