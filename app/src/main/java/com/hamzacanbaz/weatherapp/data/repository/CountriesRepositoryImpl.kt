package com.hamzacanbaz.weatherapp.data.repository

import com.hamzacanbaz.weatherapp.data.model.countries.Country
import com.hamzacanbaz.weatherapp.data.source.local.LocalDataSource
import com.hamzacanbaz.weatherapp.domain.repository.CountriesRepository
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : CountriesRepository {
    override suspend fun saveCountry(country: Country) =
        localDataSource.addToSavedCountries(country)

    override suspend fun getCountries(): List<Country> = localDataSource.getSavedCountries() ?: listOf()



}