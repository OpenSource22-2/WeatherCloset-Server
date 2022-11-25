package com.opensource.weathercloset.weather.service;

import com.opensource.weathercloset.common.exception.ErrorCode;
import com.opensource.weathercloset.common.exception.InvalidValueException;
import com.opensource.weathercloset.weather.domain.Weather;
import com.opensource.weathercloset.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Transactional
    public Weather addWeather(final Weather weather) {
        if (weather == null) throw new InvalidValueException(ErrorCode.WEATHER_NOT_NULL);
        return weatherRepository.save(weather);
    }
}