package com.opensource.weathercloset.weather.service;

import com.opensource.weathercloset.weather.domain.Weather;
import com.opensource.weathercloset.weather.dto.WeatherResponseDTO;
import com.opensource.weathercloset.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private WeatherRepository weatherRepository;

    @Transactional
    public WeatherResponseDTO addWeather(float avgTa, float minTa, float maxTa, float snow, float rain, float cloud, LocalDate date) {
        Weather weather = Weather.builder()
                .avgTa(avgTa)
                .minTa(minTa)
                .maxTa(maxTa)
                .snow(snow)
                .rain(rain)
                .cloud(cloud)
                .date(date)
                .build();
        Weather saved = weatherRepository.save(weather);
        return WeatherResponseDTO.from(saved);
    }
}