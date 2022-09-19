package com.hamzacanbaz.weatherapp.data.source.remote

import com.hamzacanbaz.weatherapp.data.model.weather.CurrentWeatherResponse
import com.hamzacanbaz.weatherapp.data.model.weatherForecast.WeatherForecastResponse
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val weatherService: WeatherService
) {
    suspend fun getCurrentWeather(
        lat: Double,
        long: Double,
        appId: String
    ): CurrentWeatherResponse = weatherService.getCurrentWeather(lat, long, appId)
    suspend fun getWeatherRequest(
        lat: Double,
        long: Double,
        appId: String
    ): WeatherForecastResponse = weatherService.getFiveDayThreeHoursForecast(lat, long, appId)
}