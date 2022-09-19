package com.hamzacanbaz.weatherapp.di

import android.app.Application
import android.content.Context
import com.hamzacanbaz.weatherapp.data.repository.WeatherRepositoryImpl
import com.hamzacanbaz.weatherapp.data.source.remote.WeatherRemoteDataSource
import com.hamzacanbaz.weatherapp.data.source.remote.WeatherService
import com.hamzacanbaz.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


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
