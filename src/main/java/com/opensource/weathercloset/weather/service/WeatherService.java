package com.opensource.weathercloset.weather.service;

import com.opensource.weathercloset.common.exception.EntityNotFoundException;
import com.opensource.weathercloset.common.exception.ErrorCode;
import com.opensource.weathercloset.common.exception.InvalidValueException;
import com.opensource.weathercloset.record.service.RecordService;
import com.opensource.weathercloset.weather.domain.Weather;
import com.opensource.weathercloset.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final RecordService recordService;

    @Scheduled(cron = "0 02 12 * * ?")
    @Transactional
    public void setWeather() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Optional<Weather> weather = weatherRepository.findByDate(yesterday);
        if (weather.isPresent())
            recordService.setWeather(weather.get());
        else
            throw new EntityNotFoundException(ErrorCode.WEATHER_NOT_FOUND);
    }

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