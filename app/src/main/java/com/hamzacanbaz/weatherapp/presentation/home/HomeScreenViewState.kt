package com.hamzacanbaz.weatherapp.presentation.home

import android.content.Context
import com.hamzacanbaz.weatherapp.data.model.weather.Coord
import com.hamzacanbaz.weatherapp.data.model.weather.CurrentWeatherResponse

class HomeScreenViewState(
    val currentWeatherResponse: CurrentWeatherResponse
) {

    fun getCoordination(context: Context): Coord = currentWeatherResponse.coord

}