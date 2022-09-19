package com.hamzacanbaz.weatherapp.domain.usecase

import com.hamzacanbaz.weatherapp.data.model.weather.CurrentWeatherResponse
import com.hamzacanbaz.weatherapp.data.model.weatherForecast.WeatherForecastResponse
import com.hamzacanbaz.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(
        lat: Double,
        long: Double,
        appId: String
    ): WeatherForecastResponse = weatherRepository.getWeatherForecast(lat, long, appId)


}