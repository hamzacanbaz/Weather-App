package com.hamzacanbaz.weatherapp.data.model.weatherForecast

data class WeatherForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Detail>,
    val message: Int
)