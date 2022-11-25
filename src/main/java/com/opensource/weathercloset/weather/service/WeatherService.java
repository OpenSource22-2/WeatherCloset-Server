package com.opensource.weathercloset.weather.service;

import com.opensource.weathercloset.weather.domain.Weather;
import com.opensource.weathercloset.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Transactional
    public Weather addWeather(final Weather createWeather) {
        if (createWeather == null) throw new IllegalArgumentException("weather cannot be null");
        return weatherRepository.save(createWeather);
    }
}