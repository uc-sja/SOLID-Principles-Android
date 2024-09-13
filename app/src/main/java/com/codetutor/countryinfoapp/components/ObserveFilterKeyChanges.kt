package com.codetutor.countryinfoapp.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import com.codetutor.countryinfoapp.repository.FilterByContinent
import com.codetutor.countryinfoapp.repository.FilterByDriveSide
import com.codetutor.countryinfoapp.viewmodel.CountryViewModel


@Composable
fun ObserveFilterKeyChanges(filterByKey: MutableState<String>,
                            selectedFilter: MutableState<String?>,
                            viewModel: CountryViewModel
) {
    val filterKey by filterByKey
    val selectedFilterValue by selectedFilter

    LaunchedEffect(filterKey, selectedFilterValue) {
        if (selectedFilterValue != null) {
            filterBy(filterKey, selectedFilterValue, viewModel)
        }
    }
}

suspend fun filterBy(
    filterKey: String,
    selectedFilterValue: String?,
    viewModel: CountryViewModel
) {
    if (filterKey.isNotEmpty()) {
       when (selectedFilterValue) {
            "Continent" -> {
                viewModel.filterCountries(FilterByContinent(filterKey))
            }
            "Drive Side" -> {
                viewModel.filterCountries(FilterByDriveSide(filterKey))
            }
        }
    } else {
        viewModel.getAllCountries()
    }
}
