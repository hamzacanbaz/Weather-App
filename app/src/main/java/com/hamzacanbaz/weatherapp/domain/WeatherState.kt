package com.hamzacanbaz.weatherapp.domain

import com.hamzacanbaz.weatherapp.data.model.weather.CurrentWeatherResponse
import com.hamzacanbaz.weatherapp.data.model.weatherForecast.WeatherForecastResponse

data class WeatherState (
    val currentWeather: CurrentWeatherResponse,
    val forecastResponse: WeatherForecastResponse
)