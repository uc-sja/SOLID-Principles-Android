package com.codetutor.countryinfoapp.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import com.codetutor.countryinfoapp.viewmodel.CountryViewModel

@Composable
fun ObserveIsLoadingChanges(isLoading: MutableState<Boolean>, viewModel: CountryViewModel) {

    LaunchedEffect(isLoading) {
        isLoading.value = viewModel.allCountries.value.isEmpty()
    }

    SideEffect {
        isLoading.value = viewModel.allCountries.value.isEmpty()
    }

}