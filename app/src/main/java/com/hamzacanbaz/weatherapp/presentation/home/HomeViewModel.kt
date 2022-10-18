package com.hamzacanbaz.weatherapp.presentation.home

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hamzacanbaz.weatherapp.data.model.countries.Country
import com.hamzacanbaz.weatherapp.domain.model.CurrentWeatherModel
import com.hamzacanbaz.weatherapp.domain.model.WeatherForecastModel
import com.hamzacanbaz.weatherapp.domain.usecase.countries.GetCountriesUseCase
import com.hamzacanbaz.weatherapp.domain.usecase.weather.GetCurrentWeatherUseCase
import com.hamzacanbaz.weatherapp.domain.usecase.weather.GetWeatherForecastUseCase
import com.hamzacanbaz.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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
    private val currentWeather =
        mutableStateOf<Resource<List<CurrentWeatherModel>>>(Resource.Loading())
    private val weatherForecast =
        mutableStateOf<Resource<List<List<WeatherForecastModel>>>>(Resource.Loading())

    private val _countries: MutableStateFlow<List<Country>> = MutableStateFlow(mutableListOf())
    val countries = _countries
    private val _countriesCurrentWeatherList: MutableStateFlow<List<CurrentWeatherModel>> =
        MutableStateFlow(
            listOf()
        )
    val countriesCurrentWeatherList: StateFlow<List<CurrentWeatherModel>> =
        _countriesCurrentWeatherList

    private val _countriesWeatherForecastList: MutableStateFlow<ArrayList<List<WeatherForecastModel>>> =
        MutableStateFlow(
            arrayListOf()
        )
    val countriesWeatherForecastList: StateFlow<List<List<WeatherForecastModel>>> =
        _countriesWeatherForecastList

    init {
        getCurrentTime()

    }

    fun getDate(): State<String> = _date
    fun getCurrentWeather(): State<Resource<List<CurrentWeatherModel>>> = currentWeather
    fun getForecast(): State<Resource<List<List<WeatherForecastModel>>>> = weatherForecast

    fun getWeatherCurrent(lat: Double, long: Double) {
        viewModelScope.launch {
            getCurrentWeatherUseCase.invoke(lat, long, appId).collect { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        println("getWeatherCurrent success ")
                        _countriesCurrentWeatherList.update {
                            it + result.data!!
                        }
                        if (countries.value.size == countriesCurrentWeatherList.value.size) {
                            currentWeather.value = Resource.Success(
                                countriesCurrentWeatherList.value
                            )
                        }
                    }
                    is Resource.Error -> {}
                }
            }
            println("size" + countriesCurrentWeatherList.value.size)

        }


    }

    fun getWeatherForecast(lat: Double, long: Double) {
        viewModelScope.launch {
            getWeatherForecastUseCase.invoke(lat, long, appId).collect { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        _countriesWeatherForecastList.value.add(result.data ?: listOf())
                        if (countries.value.size == countriesWeatherForecastList.value.size) {
                            weatherForecast.value = Resource.Success(
                                countriesWeatherForecastList.value
                            )
                        }
                    }
                    is Resource.Error -> {}
                }
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
            getCountriesUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _countries.value = result.data ?: listOf()
                        getAllWeatherForecastForCountries()

                    }
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }

    fun getAllWeatherForecastForCountries() {
        countries.value.forEach {
            getWeatherCurrent(it.lat, it.lon)
            getWeatherForecast(it.lat, it.lon)
        }
    }


}