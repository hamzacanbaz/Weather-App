package com.hamzacanbaz.weatherapp.data.source.local

import com.hamzacanbaz.weatherapp.data.model.countries.Country
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val countriesDao: CountriesDao
) {
    suspend fun addToSavedCountries(country: Country) = countriesDao.addToSavedCountries(country)
    suspend fun getSavedCountries() = countriesDao.getCountries()
}