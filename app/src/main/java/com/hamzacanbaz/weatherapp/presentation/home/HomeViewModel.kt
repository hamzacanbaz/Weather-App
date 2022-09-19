package com.hamzacanbaz.weatherapp.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamzacanbaz.weatherapp.data.model.weatherForecast.WeatherForecastResponse
import com.hamzacanbaz.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.hamzacanbaz.weatherapp.domain.usecase.GetWeatherForecastUseCase
import com.hamzacanbaz.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val appId = "d745980d02bcd1c7ae5295fceebf1c75"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase
) : ViewModel() {


    private val currentWeather = mutableStateOf<Resource<HomeScreenViewState>>(Resource.Loading())
    fun getCurrentWeather(): State<Resource<HomeScreenViewState>> = currentWeather

    private val weatherForecast =
        mutableStateOf<Resource<WeatherForecastResponse>>(Resource.Loading())

    fun getForecast(): State<Resource<WeatherForecastResponse>> = weatherForecast

    fun getCurrentWeather(lat: Double, long: Double) {
        viewModelScope.launch {
            currentWeather.value = Resource.Loading()

            try {
                currentWeather.value = Resource.Success(
                    HomeScreenViewState(
                        currentWeatherResponse = getCurrentWeatherUseCase.invoke(
                            lat, long, appId
                        )
                    )
                )
                println(currentWeather.value.data?.currentWeatherResponse)
            } catch (e: Exception) {
                currentWeather.value = Resource.Error(e.message.toString())
            }
        }

    }

    fun getWeatherForecast(lat: Double, long: Double) {
        viewModelScope.launch {
            currentWeather.value = Resource.Loading()

            try {
                weatherForecast.value = Resource.Success(
                    getWeatherForecastUseCase.invoke(lat, long, appId)
                )
                println(weatherForecast.value.data)
            } catch (e: Exception) {
                currentWeather.value = Resource.Error(e.message.toString())
            }
        }

    }

}