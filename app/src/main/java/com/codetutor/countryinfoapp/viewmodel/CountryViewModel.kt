package com.codetutor.countryinfoapp.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codetutor.countryinfoapp.data.Country
import com.codetutor.countryinfoapp.repository.CountryRepository
import kotlinx.coroutines.launch

class CountryViewModel (private val countryRepository: CountryRepository): ViewModel() {

    val allCountries: MutableState<List<Country>> = mutableStateOf(emptyList())
    val isLoading: MutableState<Boolean> = mutableStateOf(true)
    val showDeleteAlertDialog: MutableState<Boolean> = mutableStateOf(false)
    val showUpdateDialogAlert: MutableState<Boolean> = mutableStateOf(false)

    var selectedCountryForDeletion: MutableState<Country?> = mutableStateOf(null)
    var selectedCountryForUpdation: MutableState<Country?> = mutableStateOf(null)

    var selectedFilter: MutableState<String?> =  mutableStateOf(null)
    var filterByKey : MutableState<String> =  mutableStateOf("")

    init {
        viewModelScope.launch {
            fetchAndInsertAll()
        }
    }

    private suspend fun fetchAndInsertAll(){
        val job = viewModelScope.launch {
            countryRepository.fetchAndInsertAll()
        }
        job.join()
        getAllCountries()
        isLoading.value = false
    }

    suspend fun getAllCountries(){
        allCountries.value = countryRepository.getAllCountries()
    }

    // Reset the allCountries list
    suspend fun resetAllCountries(){
        getAllCountries()
    }

    suspend fun deleteCountry(){
        selectedCountryForDeletion.value?.let {
            countryRepository.deleteCountry(it)
        }
        getAllCountries()
        selectedCountryForDeletion.value = null
    }

    suspend fun updateCountry(newCapital: String){
        selectedCountryForUpdation?.let { countryMutableState ->
            countryMutableState.value?.let {
                countryRepository.updateCapital(it,newCapital)
                getAllCountries()
            }
        }
        selectedCountryForUpdation.value = null
        showUpdateDialogAlert.value = false
    }

    suspend fun filterCountryByContinent(){
       val filteredByContinent  = allCountries.value.filter { it.continents?.contains(filterByKey.value) ?: false }
        Log.i("FilterCriteria", "Filtered by Continent ${filteredByContinent.size})")
        allCountries.value = filteredByContinent
    }

    suspend fun filterCountryByDriveSide(){
        val filteredByDriveSide   = allCountries.value.filter { it.car?.side?.equals(filterByKey.value) ?: false }
        Log.i("FilterCriteria", "Filtered by Drive Side ${filteredByDriveSide.size})")
        allCountries.value = filteredByDriveSide
    }
}