package com.hamzacanbaz.weatherapp.di

import com.hamzacanbaz.weatherapp.data.repository.WeatherRepositoryImpl
import com.hamzacanbaz.weatherapp.data.source.remote.weather.WeatherRemoteDataSource
import com.hamzacanbaz.weatherapp.data.source.remote.weather.WeatherService
import com.hamzacanbaz.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesUserWeatherRemoteDataSource(
        weatherService: WeatherService
    ): WeatherRemoteDataSource {
        return WeatherRemoteDataSource(
            weatherService
        )
    }


    @Provides
    fun provideWeatherRepository(
        weatherRemoteDataSource: WeatherRemoteDataSource
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherRemoteDataSource)
    }




}
