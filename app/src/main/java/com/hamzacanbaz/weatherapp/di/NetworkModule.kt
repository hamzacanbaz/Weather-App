package com.hamzacanbaz.weatherapp.di

import com.hamzacanbaz.weatherapp.data.source.remote.countries.CountriesService
import com.hamzacanbaz.weatherapp.data.source.remote.weather.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val WEATHER_API_URL = "https://api.openweathermap.org/"
    private const val COUNTRIES_URL = "https://countriesnow.space/api/v0.1/"

    @Provides
    @Singleton
    fun provideWeatherService(): WeatherService {
        return Retrofit.Builder()
            .baseUrl(WEATHER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun provideCountriesService(): CountriesService {
        return Retrofit.Builder()
            .baseUrl(COUNTRIES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesService::class.java)
    }


}
