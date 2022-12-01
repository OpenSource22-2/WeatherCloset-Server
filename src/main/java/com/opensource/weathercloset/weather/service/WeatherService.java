package com.opensource.weathercloset.weather.service;

import com.opensource.weathercloset.common.exception.ErrorCode;
import com.opensource.weathercloset.common.exception.InvalidValueException;
import com.opensource.weathercloset.weather.domain.Weather;
import com.opensource.weathercloset.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@EnableScheduling
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Transactional
    public Weather addWeather(Weather weather) {
        if (weather == null) throw new InvalidValueException(ErrorCode.WEATHER_NOT_NULL);
        LocalDate date = weather.getDate();
        Optional<Weather> delWeather = weatherRepository.findByDate(date);
        Long weatherId;
        if (delWeather.isPresent()) {
            weatherId = delWeather.get().getId();
            weather = Weather.builder()
                    .id(weatherId)
                    .avgTa(weather.getAvgTa())
                    .minTa(weather.getMinTa())
                    .maxTa(weather.getMaxTa())
                    .rain(weather.getRain())
                    .snow(weather.getSnow())
                    .cloud(weather.getCloud())
                    .date(weather.getDate())
                    .iconType(weather.getIconType())
                    .build();
        }
        return weatherRepository.save(weather);
    }
}