package com.aaditx23.v2_assessment.ui.screens.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRecords()
    }

    when (val uiState = state.value) {
        is MainScreenState.Loading -> {
            println("Loading...")
        }
        is MainScreenState.Success -> {
            println("Records: ")
            Text(
                text = uiState.records.toRecordString()
            )
        }
        is MainScreenState.Error -> {
            println("Error: ${uiState.error}")
        }
    }
}