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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codetutor.countryinfoapp.dialogs.MyAlertDialog
import com.codetutor.countryinfoapp.components.CountryCard
import com.codetutor.countryinfoapp.database.AppDataBase
import com.codetutor.countryinfoapp.repository.CountryRepository
import com.codetutor.countryinfoapp.ui.theme.CountryInfoAppTheme
import com.codetutor.countryinfoapp.viewmodel.CountryViewModel
import com.codetutor.countryinfoapp.viewmodel.CountryViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun MainScreen(innerPaddingValues: PaddingValues) {
    val context = LocalContext.current
    //Initialise Dao
    val countryDao = AppDataBase.getDataBase(context.applicationContext)?.countryDao()
    //Initialise the Repository
    val countryRepository = countryDao?.let { CountryRepository(context, it) }
    //Initialise the ViewModel
    val viewModel: CountryViewModel =
        viewModel(factory = countryRepository?.let { CountryViewModelFactory(repository = it) })

    val countryList = viewModel.allCountries.value
    val isLoading = viewModel.isLoading.value
    val showDeleteAlertDialog = viewModel.showDeleteAlertDialog

    CountryInfoAppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPaddingValues),
            color = MaterialTheme.colorScheme.surface
        ) {
            when {
                isLoading -> {
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
}