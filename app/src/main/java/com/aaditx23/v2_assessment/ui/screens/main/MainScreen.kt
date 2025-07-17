package com.aaditx23.v2_assessment.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.aaditx23.v2_assessment.ui.components.ErrorDialog
import com.aaditx23.v2_assessment.ui.components.LoadingDialog
import com.aaditx23.v2_assessment.ui.components.JsonFlow.RecordView
import com.aaditx23.v2_assessment.ui.components.JsonFlow.SubmitSuccessDialog

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val state = viewModel.mainUiState.collectAsState()
    val submitted = viewModel.submitted.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getSubmittedFlag(context)
        viewModel.fetchRecords()
    }

    when (val mainUiState = state.value) {
        is MainScreenState.Loading -> {
            LoadingDialog("Please wait...")
        }
        is MainScreenState.Success -> {
            if (submitted.value){
                viewModel.setSubmittedFlag(context, false)
                SubmitSuccessDialog(
                    onViewSubmissions = {
                        navController.navigate("Submissions")
                    },
                    onRestart = {
                        viewModel.setSubmitted(false)
                        viewModel.restart()
                    }
                )
            }
            else{
                RecordView(records = mainUiState.records, viewModel = viewModel)
            }


        }

        is MainScreenState.Error -> {
            ErrorDialog(
                message = "An unexpected error occurred",
                onCancel = {
                    viewModel.fetchRecords()
                }
            )
        }
    }
}