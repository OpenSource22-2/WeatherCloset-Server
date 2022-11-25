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
    private int icon_type;

    @Builder
    public Weather (double avgTa, double minTa, double maxTa, double snow, double rain, double cloud, int icon_type, LocalDate date) {
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
