package com.codetutor.countryinfoapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codetutor.countryinfoapp.dialogs.MyAlertDialog
import com.codetutor.countryinfoapp.components.CountryCard
import com.codetutor.countryinfoapp.components.ObserveIsLoadingChanges
import com.codetutor.countryinfoapp.database.AppDataBase
import com.codetutor.countryinfoapp.dialogs.MyNewAlertDialog
import com.codetutor.countryinfoapp.repository.CountryRepository
import com.codetutor.countryinfoapp.ui.theme.CountryInfoAppTheme
import com.codetutor.countryinfoapp.viewmodel.CountryViewModel
import com.codetutor.countryinfoapp.viewmodel.CountryViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun MainScreen(innerPaddingValues: PaddingValues, viewModel: CountryViewModel) {
    val context = LocalContext.current

    val countryList = viewModel.allCountries.value
    val showDeleteAlertDialog = viewModel.showDeleteAlertDialog
    val showUpdateDiloag = viewModel.showUpdateDialogAlert
    val selectedCountryForUpdation = viewModel.selectedCountryForUpdation.value

    val isLoading = viewModel.isLoading

    ObserveIsLoadingChanges(isLoading = isLoading, viewModel = viewModel)

    CountryInfoAppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPaddingValues),
            color = MaterialTheme.colorScheme.surface
        ) {
            when {
                isLoading.value -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                else -> {
                    LazyColumn {
                        /*items(countryList) {
                            CountryCard(countryInfo = it,
                                showDeleteAlertDialog = showDeleteAlertDialog,
                                viewModel = viewModel)
                        }*/

                        items(items = countryList, key = { country -> country.id ?: 0 }) { country ->
                            CountryCard(
                                countryInfo = country,
                                showDeleteAlertDialog = showDeleteAlertDialog,
                                showUpdateAlertDialog = showUpdateDiloag,
                                viewModel = viewModel
                            )
                        }
                    }
                }

            }

        }
    }

    MyAlertDialog(
        showDialog = showDeleteAlertDialog,
        title = "Delete Confirmation",
        message = "Do you want to delete this country?"
    ) {
        viewModel.viewModelScope.launch {
            viewModel.deleteCountry()
        }
    }

    MyNewAlertDialog(showDialog = showUpdateDiloag,
        title = "Update Capital",
        message = "Enter new capital",
        currentCapital = selectedCountryForUpdation?.capital?.get(0) ?: "NA",
        positiveAction = {  newCapital ->
            viewModel.viewModelScope.launch {
                viewModel.updateCountry(newCapital)
            }
        })
}