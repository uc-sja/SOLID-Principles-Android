package com.codetutor.countryinfoapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codetutor.countryinfoapp.database.appdb.AppDataBase
import com.codetutor.countryinfoapp.repository.CountryRepository
import com.codetutor.countryinfoapp.screens.MainScreen
import com.codetutor.countryinfoapp.viewmodel.CountryViewModel
import com.codetutor.countryinfoapp.viewmodel.CountryViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryInfoAppScaffold(){

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val context = LocalContext.current
    //Initialise Dao
    val countryDao = AppDataBase.getDataBase(context.applicationContext)?.countryDao()
    //Initialise the Repository
    val countryRepository = countryDao?.let { CountryRepository(context, it) }
    //Initialise the ViewModel
    val viewModel: CountryViewModel =
        viewModel(factory = countryRepository?.let { CountryViewModelFactory(repository = it) })


    ObserveFilterKeyChanges(viewModel.filterByKey, viewModel.selectedFilter, viewModel)

    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(text = "CountryInfoApp", style = MaterialTheme.typography.labelLarge)
                },
//                navigationIcon = {
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "ArrowBack")
//                    }
//                },

                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                    }

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "MoreVert")
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            BottomAppBar {
                FilterCountryChips("Continent", viewModel.selectedFilter)
                FilterCountryChips("Drive Side", viewModel.selectedFilter)

                if (viewModel.selectedFilter.value != null) {
                    TextField(
                        value = viewModel.filterByKey.value,
                        onValueChange = { newValue ->
                            viewModel.filterByKey.value = newValue
                        },
                        modifier = Modifier.padding(3.dp),
                        label = { Text("") },
                        singleLine = true,
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation( )) {
                    Icon(imageVector = Icons.Filled.Filter, contentDescription = "Filter")
            }
        }

    ) { innerPaddingValues ->
        MainScreen(innerPaddingValues, viewModel)
    }
}