package com.codetutor.countryinfoapp.viewmodel

import androidx.compose.runtime.MutableState
import com.codetutor.countryinfoapp.data.Country
import com.codetutor.countryinfoapp.repository.FilterCriteria

interface ICountryViewModel {
    val allCountries: MutableState<List<Country>>
    suspend fun getAllCountries()
    suspend fun deleteCountry()
    suspend fun updateCountry(newCapital: String)
    suspend fun filterCountries(filterCriteria: FilterCriteria)
}