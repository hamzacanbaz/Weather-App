package com.hamzacanbaz.weatherapp.data.source.remote.weather

import com.hamzacanbaz.weatherapp.data.model.weather.CurrentWeatherResponse
import com.hamzacanbaz.weatherapp.data.model.weatherForecast.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") appId: String,
        @Query("units") unit: String = "metric"
    ): CurrentWeatherResponse

    @GET("data/2.5/forecast")
    suspend fun getFiveDayThreeHoursForecast(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") appId: String,
        @Query("units") unit: String = "metric"

    ): WeatherForecastResponse

}