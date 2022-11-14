package com.opensource.weathercloset.weather.repository;

import com.opensource.weathercloset.weather.domain.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
}
