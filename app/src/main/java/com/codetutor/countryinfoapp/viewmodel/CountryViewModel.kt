package com.codetutor.countryinfoapp.viewmodel

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

    private  suspend fun getAllCountries(){
        allCountries.value = countryRepository.getAllCountries()
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
}