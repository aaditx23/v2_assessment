package com.aaditx23.v2_assessment.ui.screens.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.aaditx23.v2_assessment.ui.components.ErrorDialog
import com.aaditx23.v2_assessment.ui.components.LoadingDialog
import com.aaditx23.v2_assessment.ui.screens.main.JsonFlow.RecordView

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRecords()
    }

    when (val uiState = state.value) {
        is MainScreenState.Loading -> {
            LoadingDialog("Please wait...")
        }
        is MainScreenState.Success -> {
            RecordView(records = uiState.records, viewModel = viewModel)
        }
        is MainScreenState.Error -> {
            ErrorDialog(
                message = "An unexpected error occured",
                onCancel = {
                    viewModel.fetchRecords()
                }
            )
        }
    }
}