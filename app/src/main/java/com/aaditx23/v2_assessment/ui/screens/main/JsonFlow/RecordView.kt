package com.aaditx23.v2_assessment.ui.screens.main.JsonFlow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aaditx23.v2_assessment.model.answer.Answer
import com.aaditx23.v2_assessment.model.record.Record
import com.aaditx23.v2_assessment.ui.components.ProgressBar
import com.aaditx23.v2_assessment.ui.screens.main.MainViewModel

@Composable
fun RecordView(records: List<Record>, viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    var currentRecord by remember { mutableStateOf<Record?>(records.find { it.id == uiState.currentId }) }

    LaunchedEffect(uiState.currentId) {
        currentRecord = records.find { it.id == uiState.currentId }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top= 50.dp),
        contentAlignment = Alignment.TopStart
    ){
        ProgressBar(uiState.currentId.toFloat(), records.size.toFloat())
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        currentRecord?.let { record ->
            when (record.type) {
                "multipleChoice" -> {
                    MultipleChoice(
                        record = record,
                        onSelect = { ans ->
                            viewModel.setHasValue(true)
                            viewModel.updateAnswer(uiState.currentId, ans)
                            ans.referTo?.id?.let { referToId ->
                                if (referToId == "submit") {
                                    viewModel.setShowSubmit(true)
                                } else {
                                    viewModel.setNextId(referToId)
                                }
                            } ?: run {
                                viewModel.setNextId(uiState.currentId)
                            }
                        }
                    )
                }
                "numberInput" -> {
                    NumberInput(
                        record = record,
                        onValueChange = {
                            viewModel.setHasValue(it.value != "")
                            viewModel.setHasError(it.hasError)

                            if (uiState.saveText) {
                                viewModel.updateAnswer(questionId = record.id, answer = it)
                                viewModel.setSaveText(false)
                            }
                        }
                    )
                }
                "dropdown" -> {
                    DropDown(
                        record = record,
                        onSelect = {
                            viewModel.setHasValue(true)
                            viewModel.setHasError(false)
                            if(uiState.saveText) {
                                it.referTo?.let {
                                    viewModel.setNextId(it.id)
                                }
                                viewModel.updateAnswer(questionId = record.id, answer = it)
                                viewModel.setSaveText(false)
                            }
                        }
                    )
                }
                "checkbox" -> {
                    // TODO: Handle Checkboxes
                }
                "camera" -> {
                    // TODO: Handle Camera input
                }
                "textInput" -> {
                    // TODO: Handle Text input
                }
                else -> {
                    Text("Unknown question type")
                }
            }

            Row {
                Button(
                    onClick = { if (record.skip.id != "-1") viewModel.setCurrentId(record.skip.id) },
                    enabled = record.skip.id != "-1"
                ) {
                    Text("Skip")
                }

                if (uiState.showSubmit || record.referTo?.id == "submit") {
                    Button(onClick = {
                        viewModel.setShowSubmit(false)
                    }) {
                        Text("Submit")
                    }
                } else {
                    Button(
                        onClick = {
                            val next = record.referTo?.id
                            if (!next.isNullOrEmpty()) viewModel.setCurrentId(next)
                            else if (uiState.nextId != uiState.currentId) viewModel.setCurrentId(uiState.nextId)

                            if (record.type.contains("input", ignoreCase = true)) viewModel.setSaveText(true)
                            viewModel.setHasError(false)
                            viewModel.setHasValue(false)
                        },
                        enabled = !uiState.hasError && uiState.hasValue
                    ) {
                        Text("Next")
                    }
                }
            }
        } ?: Text("Invalid question")
    }
}
