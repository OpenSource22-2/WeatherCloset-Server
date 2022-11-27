package com.opensource.weathercloset.weather.service;

import com.opensource.weathercloset.common.exception.ErrorCode;
import com.opensource.weathercloset.common.exception.InvalidValueException;
import com.opensource.weathercloset.record.domain.Record;
import com.opensource.weathercloset.weather.domain.Weather;
import com.opensource.weathercloset.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Transactional
    public void setWeather(Record record) {
        LocalDate recordDate = LocalDate.from(record.getCreatedAt());
        Optional<Weather> weather = weatherRepository.findByDate(recordDate);
        if (weather.isPresent())
            record.setWeather(weather.get());
        else {
            // 오늘 날씨인 경우 temperature 값으로 99.0의 값을 보내고, iconType 으로 "" 을 보냄
        }
    }

    @Transactional
    public Weather addWeather(Weather weather) {
        if (weather == null) throw new InvalidValueException(ErrorCode.WEATHER_NOT_NULL);
        return weatherRepository.save(weather);
    }
}