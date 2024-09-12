package com.codetutor.countryinfoapp.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codetutor.countryinfoapp.data.Country
import com.codetutor.countryinfoapp.repository.CountryRepository
import com.codetutor.countryinfoapp.repository.FilterCriteria
import com.codetutor.countryinfoapp.repository.ICountryRepository
import kotlinx.coroutines.launch

class CountryViewModel (private val countryRepository: ICountryRepository): ViewModel(), ICountryViewModel {

    override val allCountries: MutableState<List<Country>> = mutableStateOf(emptyList())
    val isLoading: MutableState<Boolean> = mutableStateOf(true)
    val showDeleteAlertDialog: MutableState<Boolean> = mutableStateOf(false)
    val showUpdateDialogAlert: MutableState<Boolean> = mutableStateOf(false)

    var selectedCountryForDeletion: MutableState<Country?> = mutableStateOf(null)
    var selectedCountryForUpdation: MutableState<Country?> = mutableStateOf(null)

    var selectedFilter: MutableState<String?> =  mutableStateOf(null)
    var filterByKey : MutableState<String> =  mutableStateOf("")

    val filteredCountryList : MutableState<List<Country>> = mutableStateOf(emptyList())

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

    override suspend fun getAllCountries(){
        allCountries.value = countryRepository.getAllCountries()
    }

    override suspend fun deleteCountry(){
        selectedCountryForDeletion.value?.let {
            countryRepository.deleteCountry(it)
        }
        getAllCountries()
        selectedCountryForDeletion.value = null
    }

    override suspend fun updateCountry(newCapital: String){
        selectedCountryForUpdation?.let { countryMutableState ->
            countryMutableState.value?.let {
                countryRepository.updateCapital(it,newCapital)
                getAllCountries()
            }
        }
        selectedCountryForUpdation.value = null
        showUpdateDialogAlert.value = false
    }

    override suspend fun filterCountries(filterCriteria: FilterCriteria) {
        filterCriteria?.let { criteria ->
            allCountries.value = countryRepository.filterCountries(criteria)
        }
    }
}