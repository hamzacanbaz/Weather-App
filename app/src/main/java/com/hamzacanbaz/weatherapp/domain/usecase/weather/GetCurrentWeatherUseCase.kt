package com.hamzacanbaz.weatherapp.domain.usecase.weather

import com.hamzacanbaz.weatherapp.data.model.weather.toCurrentWeatherModel
import com.hamzacanbaz.weatherapp.domain.model.CurrentWeatherModel
import com.hamzacanbaz.weatherapp.domain.repository.WeatherRepository
import com.hamzacanbaz.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(
        lat: Double,
        long: Double,
        appId: String
    ): Flow<Resource<CurrentWeatherModel>> = flow {
        emit(Resource.Loading())
        try {
            emit(
                Resource.Success(
                    weatherRepository.getCurrentWeather(lat, long, appId).toCurrentWeatherModel()
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: e.message.toString()))
        }

    }


}