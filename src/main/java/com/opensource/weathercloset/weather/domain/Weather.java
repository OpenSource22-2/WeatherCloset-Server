package com.opensource.weathercloset.weather.domain;

import com.opensource.weathercloset.common.domain.DateTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Weather extends DateTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private LocalDateTime datetime;

    private int temperature;

}
