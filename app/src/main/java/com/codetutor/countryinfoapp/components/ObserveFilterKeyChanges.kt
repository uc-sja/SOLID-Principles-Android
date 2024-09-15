package com.codetutor.countryinfoapp.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import com.codetutor.countryinfoapp.repository.FilterByContinent
import com.codetutor.countryinfoapp.repository.FilterByDriveSide
import com.codetutor.countryinfoapp.util.FilterCriteriaFactoryProvider
import com.codetutor.countryinfoapp.viewmodel.CountryViewModel
import com.codetutor.countryinfoapp.viewmodel.ICountryViewModel


@Composable
fun ObserveFilterKeyChanges(filterByKey: MutableState<String>,
                            selectedFilter: MutableState<String?>,
                            viewModel: ICountryViewModel
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
    viewModel: ICountryViewModel
) {
    if (filterKey.isNotEmpty()) {
        val  factory = FilterCriteriaFactoryProvider.getFactory(selectedFilterValue!!)
        val filterCriteria = factory?.createFilterCriteria(filterKey)
        filterCriteria?.let {
            viewModel.filterCountries(it)
        }
    } else {
        viewModel.getAllCountries()
    }
}
