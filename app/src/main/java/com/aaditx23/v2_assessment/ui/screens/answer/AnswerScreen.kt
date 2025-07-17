package com.aaditx23.v2_assessment.ui.screens.answer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.aaditx23.v2_assessment.model.Answer
import com.aaditx23.v2_assessment.ui.components.BackButton
import com.aaditx23.v2_assessment.ui.components.ErrorDialog
import com.aaditx23.v2_assessment.ui.components.JsonFlow.Camera
import com.aaditx23.v2_assessment.ui.components.JsonFlow.CheckBox
import com.aaditx23.v2_assessment.ui.components.JsonFlow.DropDown
import com.aaditx23.v2_assessment.ui.components.JsonFlow.MultipleChoice
import com.aaditx23.v2_assessment.ui.components.JsonFlow.NumberInput
import com.aaditx23.v2_assessment.ui.components.JsonFlow.TextInput
import com.aaditx23.v2_assessment.ui.components.LoadingDialog

@Composable
fun AnswerScreen(
    viewModel: AnswerScreenViewModel = hiltViewModel(),
    submissionId: Long,
    navController: NavHostController
){

    val state = viewModel.answerScreenState.collectAsState()
    val records = viewModel.records.collectAsState()

    val skipped = Answer(
        questionType = "skipped",
        referTo = null,
        value = "",
        valueId = null,
        hasError = false
    )


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
            val answers = answerUiState.answers

            LazyColumn(
                modifier = Modifier
                    .padding(top = 50.dp, bottom = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Answers for Submission #${submissionId}",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(bottom = 16.dp),
                            thickness = 1.dp,
                        )
                    }
                }
                itemsIndexed(records.value) {index, record ->
                    var isSkipped by remember { mutableStateOf(false) }
                    fun matchAnswer(): Answer {
                        val findAnswer = answers.find { it.questionType == record.type }?.toAnswer()
                        if (findAnswer == null) isSkipped = true

                        return findAnswer?: skipped
                    }


                    when (record.type) {
                        "multipleChoice" -> {
                            MultipleChoice(
                                record = record,
                                onSelect = {},
                                answer = matchAnswer()
                            )
                        }
                        "numberInput" -> {
                            NumberInput(
                                record = record,
                                onValueChange = {},
                                answer = matchAnswer()
                            )
                        }
                        "dropdown" -> {
                            DropDown(
                                record = record,
                                onSelect = {},
                                answer = matchAnswer()
                            )
                        }
                        "checkbox" -> {
                            CheckBox(
                                record = record,
                                onSave = {},
                                answer = matchAnswer()
                            )
                        }
                        "camera" -> {
                            Camera(
                                record = record,
                                onSave = {},
                                answer = matchAnswer()
                            )
                        }
                        "textInput" -> {
                            TextInput(
                                record = record,
                                onChange = {},
                                answer = matchAnswer()
                            )
                        }
                        else -> {
                            Text("Unknown question type")
                        }
                    }

                    if(isSkipped){
                        Text(
                            text = "Skipped Answer",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    if(index != records.value.lastIndex){
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            thickness = 0.7.dp,
                        )
                    }

                }
            }

            BackButton(
                onClick = {
                    navController.navigateUp()
                }
            )

        }
    }
}