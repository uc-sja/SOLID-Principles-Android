package com.codetutor.countryinfoapp.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
    Log.i("FilterCriteria", "Selected Filter value $selectedFilterValue!!")
    Log.i("FilterCriteria", "Selected Filter filterKey $filterKey")
    if (filterKey.isNotEmpty()) {
        //val filterCriteria = determineFilterCriteria(selectedFilterValue!!, filterKey)
       when (selectedFilterValue) {
            "Continent" -> {
                //ToDo - Filter by Continent
                Log.i("FilterCriteria", "Filtering by Continent $filterKey")
                viewModel.filterCountryByContinent()
            }
            "Drive Side" -> {
                //ToDo - Filter by driveSide
                Log.i("FilterCriteria", "Filtering by driveSide $filterKey")
                viewModel.filterCountryByDriveSide()
            }
        }

        Log.i("FilterCriteria", "After filtering the size is  ${viewModel.allCountries.value.size}")
    } else {
        Log.i("FilterCriteria", "No filter criteria selected")
        viewModel.getAllCountries()
    }
}
