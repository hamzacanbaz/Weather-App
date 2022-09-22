package com.hamzacanbaz.weatherapp.presentation.addLocation

import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import javax.inject.Inject


@HiltViewModel
class AddLocationScreenViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {


    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private val _countries: MutableState<ArrayList<String>> =
        mutableStateOf(arrayListOf())
    val countries: State<List<String>> = _countries

    init {
        readCountries()
    }


    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun readCountries() {
        val jsonData = getApplication<Application>().resources.openRawResource(
            getApplication<Application>().resources.getIdentifier(
                "countries", "raw", getApplication<Application>().packageName
            )
        ).bufferedReader().use { it.readText() }

        val outputJsonString = JSONObject(jsonData)
        val data = outputJsonString.getJSONArray("data") as JSONArray
        for (i in 0 until data.length()) {
            val country = data.getJSONObject(i).get("country")
            val cities = data.getJSONObject(i).get("cities")
            for (i in 0 until (cities as JSONArray).length()) {
                _countries.value.add(cities.getString(i))
            }
        }
        _countries.value.sort()
    }

    fun getLocationFromName(locationName: String): Pair<Double, Double> {

        val mGeocoder = Geocoder(getApplication(), Locale.getDefault())
        val addressList: List<Address>
        var address = Address(Locale.getDefault())
        // Reverse-Geocoding starts
        try {
            addressList =
                mGeocoder.getFromLocationName(locationName, 1) as List<Address>
            address = addressList[0]

        } catch (e: IOException) {
            Log.e("AddLocation", e.message.toString())
        }


        println("address -> ${address.latitude} ${address.longitude}")
        return Pair(address.latitude, address.longitude)
    }

}