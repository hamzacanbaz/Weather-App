package com.hamzacanbaz.weatherapp.util.enums

import androidx.annotation.DrawableRes
import com.hamzacanbaz.weatherapp.R

enum class WeatherDescriptions(@DrawableRes val icon: Int) {
    CLEAR(R.drawable.sun),
    CLEAR_SKY(R.drawable.sun),
    FEW_CLOUDS(R.drawable.cloudy),
    CLOUDS(R.drawable.cloud),
    BROKEN_CLOUDS(R.drawable.cloudcomputing),
    SHOWER_RAIN(R.drawable.rainy),
    LIGHT_RAIN(R.drawable.rainy),
    RAIN(R.drawable.rainy),
    THUNDERSTORM(R.drawable.storm),
    SNOW(R.drawable.snowy),
    MIST(R.drawable.windy),
    SMOKE(R.drawable.windy),
    HAZE(R.drawable.windy),
    DUST(R.drawable.windy),
    FOG(R.drawable.windy),
    SAND(R.drawable.windy),
    ASH(R.drawable.windy),
    SQUALL(R.drawable.windy),
    TORNADO(R.drawable.windy),
    OVERCAST_CLOUDS(R.drawable.cloudcomputing),
    DRIZZLE(R.drawable.rainy)
}