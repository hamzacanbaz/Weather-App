package com.hamzacanbaz.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.hamzacanbaz.weatherapp.data.source.local.CountriesDao
import com.hamzacanbaz.weatherapp.data.source.local.CountriesRoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {

    @Provides
    @Singleton
    fun providesCountriesRoomDb(@ApplicationContext applicationContext: Context): CountriesRoomDb =
        Room.databaseBuilder(
            applicationContext,
            CountriesRoomDb::class.java,
            "countries_database.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideCountriesDAO(countriesRoomDb: CountriesRoomDb): CountriesDao =
        countriesRoomDb.countriesDao()
}