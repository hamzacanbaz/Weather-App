package com.hamzacanbaz.weatherapp.data.repository

import com.hamzacanbaz.weatherapp.data.model.weather.CurrentWeatherResponse
import com.hamzacanbaz.weatherapp.data.model.weatherForecast.WeatherForecastResponse
import com.hamzacanbaz.weatherapp.data.source.remote.WeatherRemoteDataSource
import com.hamzacanbaz.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {
    override suspend fun getCurrentWeather(
        lat: Double,
        long: Double,
        appId: String
    ): CurrentWeatherResponse {
        return remoteDataSource.getCurrentWeather(lat, long, appId)
    }

    override suspend fun getWeatherForecast(
        lat: Double,
        long: Double,
        appId: String
    ): WeatherForecastResponse {
        return remoteDataSource.getWeatherRequest(lat, long, appId)
    }

}