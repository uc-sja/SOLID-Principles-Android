package com.codetutor.countryinfoapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.codetutor.countryinfoapp.data.Country

@Dao
interface CountryDao {
    // Add your queries here

    //select
    @Query("SELECT * from Country")
    suspend fun getAllCountries(): List<Country>

    @Query("SELECT * from Country WHERE continents LIKE :continent")
    suspend fun getCountriesByContinent(continent: String): List<Country>

    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<Country>)

    //delete
    @Delete
    suspend fun delete(country: Country): Int
}