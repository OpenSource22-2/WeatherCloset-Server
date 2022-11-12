package com.opensource.weathercloset.weather.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Weather {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private LocalDateTime datetime;

    private int temperature;

}
