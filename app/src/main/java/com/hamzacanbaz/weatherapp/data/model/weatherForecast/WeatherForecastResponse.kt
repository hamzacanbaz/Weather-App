package com.hamzacanbaz.weatherapp.data.model.weatherForecast

import com.hamzacanbaz.weatherapp.domain.model.WeatherForecastModel
import com.hamzacanbaz.weatherapp.util.enums.WeatherDescriptions
import java.math.RoundingMode

data class WeatherForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Detail>,
    val message: Int
)

fun WeatherForecastResponse.toWeatherForecastList(): List<WeatherForecastModel> {
    val weatherForecastList = arrayListOf<WeatherForecastModel>()
    list.forEach {
        weatherForecastList.add(
            WeatherForecastModel(
                it.dt_txt.split(" ")[1].removeSuffix(":00"),
                it.dt_txt.split(" ")[0].removePrefix("2022-"),
                icon = WeatherDescriptions.valueOf(
                    it.weather[0].main.replace(
                        " ",
                        "_"
                    ).uppercase()
                ).icon,
                it.main.temp.toBigDecimal().setScale(1, RoundingMode.HALF_EVEN).toString() + "°"
            )
        )
    }
    return weatherForecastList.toList()
}