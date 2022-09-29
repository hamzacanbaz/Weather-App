package com.hamzacanbaz.weatherapp.di

import com.hamzacanbaz.weatherapp.data.repository.CountriesRepositoryImpl
import com.hamzacanbaz.weatherapp.data.repository.WeatherRepositoryImpl
import com.hamzacanbaz.weatherapp.data.source.local.CountriesDao
import com.hamzacanbaz.weatherapp.data.source.local.LocalDataSource
import com.hamzacanbaz.weatherapp.data.source.remote.weather.WeatherRemoteDataSource
import com.hamzacanbaz.weatherapp.data.source.remote.weather.WeatherService
import com.hamzacanbaz.weatherapp.domain.repository.CountriesRepository
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
    fun providesCountriesLocalDataSource(
        countriesDao: CountriesDao
    ): LocalDataSource {
        return LocalDataSource(
            countriesDao
        )
    }


    @Provides
    fun provideWeatherRepository(
        weatherRemoteDataSource: WeatherRemoteDataSource
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherRemoteDataSource)
    }


    @Provides
    fun provideCountriesRepository(
        localDataSource: LocalDataSource
    ): CountriesRepository {
        return CountriesRepositoryImpl(localDataSource)
    }


}
