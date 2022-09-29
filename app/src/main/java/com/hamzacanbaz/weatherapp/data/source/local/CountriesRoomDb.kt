package com.hamzacanbaz.weatherapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hamzacanbaz.weatherapp.data.model.countries.Country

@Database(entities = [Country::class], version = 2, exportSchema = false)
abstract class CountriesRoomDb : RoomDatabase() {
    abstract fun countriesDao(): CountriesDao
}