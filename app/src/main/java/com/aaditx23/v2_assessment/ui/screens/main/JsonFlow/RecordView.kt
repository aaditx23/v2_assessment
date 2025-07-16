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
        viewModel.resetErrorAndHasValue()
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
                        onSelect = {
                            viewModel.updateValueErrorAndNextId(it)
                            viewModel.updateAnswer(record.id, it, record.referTo)

                        }
                    )
                }
                "numberInput" -> {
                    NumberInput(
                        record = record,
                        onValueChange = {
                            viewModel.updateValueErrorAndNextId(it)
                            currentAnswer = it
                        }
                    )
                }
                "dropdown" -> {
                    DropDown(
                        record = record,
                        onSelect = {
                            viewModel.updateValueErrorAndNextId(it)
                            viewModel.updateAnswer(record.id, it, record.referTo)
                        }
                    )
                }
                "checkbox" -> {
                    CheckBox(
                        record = record,
                        onSave = {
                            viewModel.updateValueErrorAndNextId(it)
                            currentAnswer = it
                        }
                    )
                }
                "camera" -> {
                    currentAnswer?.let {
                        if (it.value.isNotEmpty()){
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
                                viewModel.updateValueErrorAndNextId(it)
                                viewModel.updateAnswer(record.id, it, record.referTo)
                            }
                        )
                    }
                }
                "textInput" -> {
                    TextInput(
                        record = record,
                        onChange = {
                            viewModel.updateValueErrorAndNextId(it)
                            currentAnswer = it
                        }
                    )
                }
                else -> {
                    Text("Unknown question type")
                }
            }

            Row {
                if(record.skip.id != "-1"){
                    Button(
                        onClick = { if (record.skip.id != "-1") viewModel.setCurrentId(record.skip.id) },
                        enabled = record.skip.id != "-1"
                    ) {
                        Text("Skip")
                    }
                }

                if (uiState.showSubmit || record.referTo?.id == "submit") {
                    Button(onClick = {
                        viewModel.setShowSubmit(false)
                    }) {
                        Text("Submit")
                    }
                } else if (listOf("input", "checkBox").any { record.type.contains(it, ignoreCase = true) }) {
                    Button(
                        onClick = {
                            currentAnswer?.let {
                                viewModel.updateAnswer(
                                    questionId = uiState.currentId,
                                    answer = it,
                                    referTo = currentRecord!!.referTo
                                )
                            }

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
