package com.aaditx23.v2_assessment.ui.screens.main.JsonFlow

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
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
        Column(
            horizontalAlignment = Alignment.End
        ) {
            ProgressBar(uiState.currentId.toFloat(), records.size.toFloat())

            IconButton(
                onClick = {
                    viewModel.setCurrentId("1")
                    viewModel.clearAnswers()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = "Refresh",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(24.dp)
                )
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        currentRecord?.let { record ->
            var currentAnswer by remember(record.id) { mutableStateOf<Answer?>(null) }
            when (record.type) {
                "multipleChoice" -> {
                    MultipleChoice(
                        record = record,
                        onSelect = { ans ->
                            currentAnswer = ans
                            viewModel.setHasValue(ans.value.isNotEmpty())
                            viewModel.setHasError(ans.hasError)
                            ans.referTo?.let { it ->
                                viewModel.setNextId(it.id)
                            }
                        }
                    )
                }
                "numberInput" -> {
                    NumberInput(
                        record = record,
                        onValueChange = {
                            viewModel.setHasValue(it.value.isNotEmpty())
                            viewModel.setHasError(it.hasError)
                            currentAnswer = it
                        }
                    )
                }
                "dropdown" -> {
                    DropDown(
                        record = record,
                        onSelect = {
                            viewModel.setHasValue(it.referTo != null)
                            viewModel.setHasError(it.hasError)
                            it.referTo?.let {
                                viewModel.setNextId(it.id)
                            }
                            currentAnswer = it
                        }
                    )
                }
                "checkbox" -> {
                    CheckBox(
                        record = record,
                        onSave = {
                            viewModel.setHasValue(it.value.isNotEmpty())
                            viewModel.setHasError(it.hasError)
                            it.referTo?.let{
                                viewModel.setNextId(it.id)
                            }
                            currentAnswer = it
                        }
                    )
                }
                "camera" -> {
                    println(currentAnswer)
                    currentAnswer?.let {
                        if (it.value.isNotEmpty()){
                            println("CURENT VALUE ${it.value}")
                            AsyncImage(
                                model = it.value,
                                contentDescription = "Image ",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(200.dp)
                                    .padding(20.dp)
                            )
                        }
                    }?: run{
                        Camera(
                            record = record,
                            onSave = {
                                viewModel.setHasValue(it.value.isNotEmpty())
                                viewModel.setHasError(it.hasError)
                                currentAnswer = it
                            }
                        )
                    }
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
                            currentAnswer?.let {
                                viewModel.updateAnswer(
                                    questionId = uiState.currentId,
                                    answer = it
                                )
                            }
                            val next = record.referTo?.id
                            if (!next.isNullOrEmpty()) viewModel.setCurrentId(next)
                            else if (uiState.nextId != uiState.currentId) viewModel.setCurrentId(uiState.nextId)

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
