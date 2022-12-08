package com.opensource.weathercloset.weather.repository;

import com.opensource.weathercloset.weather.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Optional<Weather> findByDate(LocalDate date);
}
