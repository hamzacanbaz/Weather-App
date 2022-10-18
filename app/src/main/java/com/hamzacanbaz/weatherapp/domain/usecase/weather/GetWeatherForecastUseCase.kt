package com.hamzacanbaz.weatherapp.domain.usecase.weather

import com.hamzacanbaz.weatherapp.data.model.weatherForecast.toWeatherForecastList
import com.hamzacanbaz.weatherapp.domain.model.WeatherForecastModel
import com.hamzacanbaz.weatherapp.domain.repository.WeatherRepository
import com.hamzacanbaz.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(
        lat: Double,
        long: Double,
        appId: String
    ): Flow<Resource<List<WeatherForecastModel>>> = flow {
        emit(Resource.Loading())
        try {
            emit(
                Resource.Success(
                    weatherRepository.getWeatherForecast(lat, long, appId).toWeatherForecastList()
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: e.message.toString()))
        }

    }


}