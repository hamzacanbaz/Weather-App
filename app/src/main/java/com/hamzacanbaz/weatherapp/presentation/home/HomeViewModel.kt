package com.hamzacanbaz.weatherapp.presentation.home

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hamzacanbaz.weatherapp.data.model.countries.Country
import com.hamzacanbaz.weatherapp.data.model.weather.toCurrentWeatherModel
import com.hamzacanbaz.weatherapp.data.model.weatherForecast.toWeatherForecastList
import com.hamzacanbaz.weatherapp.domain.model.CurrentWeatherModel
import com.hamzacanbaz.weatherapp.domain.model.WeatherForecastModel
import com.hamzacanbaz.weatherapp.domain.usecase.countries.GetCountriesUseCase
import com.hamzacanbaz.weatherapp.domain.usecase.weather.GetCurrentWeatherUseCase
import com.hamzacanbaz.weatherapp.domain.usecase.weather.GetWeatherForecastUseCase
import com.hamzacanbaz.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

private const val appId = "d745980d02bcd1c7ae5295fceebf1c75"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    application: Application
) : AndroidViewModel(application) {

    private val _date = mutableStateOf("")
    private val currentWeather = mutableStateOf<Resource<CurrentWeatherModel>>(Resource.Loading())
    private val weatherForecast =
        mutableStateOf<Resource<List<WeatherForecastModel>>>(Resource.Loading())

    private val _countries: MutableStateFlow<List<Country>> = MutableStateFlow(listOf())
    val countries = _countries

    init {
        getCurrentTime()

    }

    fun getDate(): State<String> = _date
    fun getCurrentWeather(): State<Resource<CurrentWeatherModel>> = currentWeather
    fun getForecast(): State<Resource<List<WeatherForecastModel>>> = weatherForecast

    fun getWeatherCurrent(lat: Double, long: Double) {
        viewModelScope.launch {
            currentWeather.value = Resource.Loading()

            try {
                currentWeather.value = Resource.Success(
                    getCurrentWeatherUseCase.invoke(
                        lat, long, appId
                    ).toCurrentWeatherModel()

                )
                println(currentWeather.value.data)
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
                        .toWeatherForecastList()
                )
                println(weatherForecast.value.data)
            } catch (e: Exception) {
                currentWeather.value = Resource.Error(e.message.toString())
            }
        }
    }

    fun getCurrentTime() {
        val sdf = SimpleDateFormat("dd MMMM HH:mm")
        val currentDateAndTime = sdf.format(java.util.Date())
        _date.value = currentDateAndTime
    }

    fun getSavedLocationsFromLocalDb(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {

            try {
                Log.i("Get Locations", "Successful")
                _countries.value = getCountriesUseCase.invoke()
            } catch (e: Exception) {
                Log.e("Get Locations", e.localizedMessage.toString())
            }
        }
    }


}