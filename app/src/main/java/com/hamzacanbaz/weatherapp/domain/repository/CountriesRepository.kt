package com.hamzacanbaz.weatherapp.domain.repository

import com.hamzacanbaz.weatherapp.data.model.countries.Country

interface CountriesRepository {
    suspend fun saveCountry(country: Country)
    suspend fun getCountries() : List<Country>
}