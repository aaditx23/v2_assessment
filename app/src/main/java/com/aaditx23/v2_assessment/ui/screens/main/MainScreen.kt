package com.aaditx23.v2_assessment.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.aaditx23.v2_assessment.data.local.SharedPreferences
import com.aaditx23.v2_assessment.ui.components.ErrorDialog
import com.aaditx23.v2_assessment.ui.components.JsonFlow.SubmitSuccessDialog
import com.aaditx23.v2_assessment.ui.components.LoadingDialog
import com.aaditx23.v2_assessment.ui.screens.main.child.RecordView

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val state = viewModel.mainScreenState.collectAsState()
    val submitted = SharedPreferences.submitted.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        SharedPreferences.isSubmitted(context)
        viewModel.fetchRecords()
    }

    when (val mainUiState = state.value) {
        is MainScreenState.Loading -> {
            LoadingDialog("Please wait...")
        }
        is MainScreenState.Success -> {
            if (submitted.value){
                SubmitSuccessDialog(
                    onViewSubmissions = {
                        SharedPreferences.setSubmitted(context, false)
                        navController.navigate("Submissions")
                    },
                    onRestart = {
                        SharedPreferences.setSubmitted(context, false)
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