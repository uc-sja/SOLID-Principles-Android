package com.codetutor.countryinfoapp.repository

import android.content.Context
import com.codetutor.countryinfoapp.data.Country
import com.codetutor.countryinfoapp.database.CountryDao
import com.codetutor.countryinfoapp.util.getCountryList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class CountryRepository(private val context: Context, private val countryDao: CountryDao) {
    private val contextForRepository = context.applicationContext
    private var allCountries: List<Country> = emptyList()

    //fetchAndInsertAll
    suspend fun fetchAndInsertAll() = withContext(Dispatchers.IO){
        val allCountries = getAllCountries()
        if(allCountries.isNotEmpty()){
            return@withContext
        }else {
            val countryMutableList = getCountryList(contextForRepository)
            val countryList: List<Country> = countryMutableList.toList()
            countryDao.insertAll(countryList)
            return@withContext
        }
    }

    suspend fun getAllCountries(): List<Country> = withContext(Dispatchers.IO){
        if(allCountries.isNotEmpty()){
            return@withContext allCountries
        }else {
            allCountries = countryDao.getAllCountries()
            return@withContext allCountries
        }
    }

    suspend fun deleteCountry(country: Country) = withContext(Dispatchers.IO){
        countryDao.delete(country)
        allCountries = countryDao.getAllCountries()
    }

    suspend fun updateCapital(country: Country, newCapital: String) = withContext(Dispatchers.IO){
        val parsedString = "[\"${newCapital}\"]"
        val parsedArray = Json.decodeFromString<List<String>>(parsedString)
        val updatedCountry = country?.copy(capital = parsedArray)
        updatedCountry.let {
            countryDao.updateCountry(it!!)
            allCountries = countryDao.getAllCountries()
        }
    }
}