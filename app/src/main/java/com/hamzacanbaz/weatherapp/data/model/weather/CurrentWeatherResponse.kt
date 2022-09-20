package com.hamzacanbaz.weatherapp.data.model.weather

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.hamzacanbaz.weatherapp.domain.model.CurrentWeatherModel
import com.hamzacanbaz.weatherapp.util.enum.WeatherDescriptions
import java.math.RoundingMode

data class CurrentWeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val rain: Rain,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

fun CurrentWeatherResponse.toCurrentWeatherModel(): CurrentWeatherModel {
    return CurrentWeatherModel(
        weather[0].main,
        weather[0].description.capitalize(Locale.current),
        main.temp.toInt().toString(),
        main.feels_like.toBigDecimal().setScale(1, RoundingMode.HALF_EVEN).toString(),
        main.temp_min.toInt().toString(),
        main.temp_max.toInt().toString(),
        main.humidity.toString(),
        wind.speed.toBigDecimal().setScale(1, RoundingMode.HALF_EVEN).toString(),
        name,
        weatherIcon = WeatherDescriptions.valueOf(
            weather[0].description.replace(
                " ",
                "_"
            ).uppercase()
        ).icon,
        coord.lat,
        coord.lon
    )
}