package com.hamzacanbaz.weatherapp.domain.usecase.countries

import com.hamzacanbaz.weatherapp.data.model.countries.Country
import com.hamzacanbaz.weatherapp.domain.repository.CountriesRepository
import com.hamzacanbaz.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<Country>>> = flow {
        emit(Resource.Loading())
        try {
            emit(
                Resource.Success(
                    countriesRepository.getCountries()
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: e.message.toString()))
        }
    }


}