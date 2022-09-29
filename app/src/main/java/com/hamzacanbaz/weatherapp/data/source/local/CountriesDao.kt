package com.hamzacanbaz.weatherapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hamzacanbaz.weatherapp.data.model.countries.Country

@Dao
interface CountriesDao {

    @Insert
    suspend fun addToSavedCountries(country: Country)

    @Query("SELECT * FROM countries")
    suspend fun getCountries(): List<Country>?
//
//    @Query("SELECT title FROM favorites")
//    suspend fun getFavoritesTitles(): List<String>?
//
//    @Query("DELETE FROM favorites WHERE id = :idInput")
//    suspend fun deleteFromFavorites(idInput: Int)
//
//    @Query("DELETE FROM favorites")
//    fun clearFavorites()
}