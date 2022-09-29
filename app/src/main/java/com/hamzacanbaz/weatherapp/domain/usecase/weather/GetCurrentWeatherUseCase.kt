package com.hamzacanbaz.weatherapp.domain.usecase.weather

import com.hamzacanbaz.weatherapp.data.model.weather.CurrentWeatherResponse
import com.hamzacanbaz.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(
        lat: Double,
        long: Double,
        appId: String
    ): CurrentWeatherResponse = weatherRepository.getCurrentWeather(lat, long, appId)


}