package com.hamzacanbaz.weatherapp.domain.usecase.countries

import com.hamzacanbaz.weatherapp.data.model.countries.Country
import com.hamzacanbaz.weatherapp.domain.repository.CountriesRepository
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository
) {

    suspend operator fun invoke() = countriesRepository.getCountries()


}