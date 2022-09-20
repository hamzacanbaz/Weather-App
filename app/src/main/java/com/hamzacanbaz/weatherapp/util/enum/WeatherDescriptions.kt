package com.hamzacanbaz.weatherapp.util.enum

import androidx.annotation.DrawableRes
import com.hamzacanbaz.weatherapp.R

enum class WeatherDescriptions(@DrawableRes val icon: Int, ) {
    CLEAR_SKY(R.drawable.sun),
    FEW_CLOUDS(R.drawable.cloudy),
    SCATTERED_CLOUDS(R.drawable.cloud),
    BROKEN_CLOUDS(R.drawable.cloudcomputing),
    SHOWER_RAIN(R.drawable.rainy),
    RAIN(R.drawable.rainy),
    THUNDERSTORM(R.drawable.storm),
    SNOW(R.drawable.snowy),
    MIST(R.drawable.windy)
}