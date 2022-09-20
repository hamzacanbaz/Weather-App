package com.hamzacanbaz.weatherapp.domain.model

import androidx.annotation.DrawableRes

data class CurrentWeatherModel(
    val weatherMain: String,
    val weatherDesc: String,
    val mainTemp: String,
    val mainFeelsLike: String,
    val mainTempMin: String,
    val mainTempMax: String,
    val mainHumidity: String,
    val windSpeed: String,
    val locationName: String,
    @DrawableRes
    val weatherIcon: Int,
    val lat: Double,
    val lon: Double
)