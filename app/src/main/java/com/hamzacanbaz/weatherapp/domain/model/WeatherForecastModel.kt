package com.hamzacanbaz.weatherapp.domain.model

import androidx.annotation.DrawableRes

data class WeatherForecastModel(
    val time: String,
    val date: String,
    @DrawableRes
    val icon: Int,
    val temp: String
)