package com.hamzacanbaz.weatherapp.domain.repository

import com.hamzacanbaz.weatherapp.data.model.weather.CurrentWeatherResponse
import com.hamzacanbaz.weatherapp.data.model.weatherForecast.WeatherForecastResponse

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, long: Double, appId: String): CurrentWeatherResponse
    suspend fun getWeatherForecast(lat: Double, long: Double, appId: String): WeatherForecastResponse
}