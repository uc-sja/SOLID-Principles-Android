package com.codetutor.countryinfoapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.codetutor.countryinfoapp.data.Country

class CountryUIViewModel: ViewModel() {
    val showDeleteAlertDialog: MutableState<Boolean> = mutableStateOf(false)
    val showUpdateDialogAlert: MutableState<Boolean> = mutableStateOf(false)

    var selectedCountryForDeletion: MutableState<Country?> = mutableStateOf(null)
    var selectedCountryForUpdation: MutableState<Country?> = mutableStateOf(null)

    var selectedFilter: MutableState<String?> =  mutableStateOf(null)
    var filterByKey : MutableState<String> =  mutableStateOf("")


}