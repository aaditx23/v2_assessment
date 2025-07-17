package com.aaditx23.v2_assessment.ui.screens.answer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.aaditx23.v2_assessment.ui.components.BackButton
import com.aaditx23.v2_assessment.ui.components.ErrorDialog
import com.aaditx23.v2_assessment.ui.components.LoadingDialog

@Composable
fun AnswerScreen(
    viewModel: AnswerScreenViewModel = hiltViewModel(),
    submissionId: Long,
    navController: NavHostController
){

    val state = viewModel.answerScreenState.collectAsState()
    val records = viewModel.records.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.refresh(submissionId)
    }

    when(val answerUiState = state.value){
        is AnswerScreenState.Error -> {
            ErrorDialog(
                message = answerUiState.error,
                onCancel = {
                    viewModel.refresh(submissionId)
                }
            )
        }
        AnswerScreenState.Loading -> {
            LoadingDialog("Please wait...")
        }
        is AnswerScreenState.ShowAnswers -> {

            BackButton(
                onClick = {
                    navController.navigateUp()
                }
            )
            Text("${answerUiState.answers}")
        }
    }
}