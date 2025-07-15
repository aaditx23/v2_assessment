package com.aaditx23.v2_assessment.ui.screens.main.JsonFlow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aaditx23.v2_assessment.model.answer.Answer
import com.aaditx23.v2_assessment.model.record.Record
import com.aaditx23.v2_assessment.ui.screens.main.MainViewModel

@Composable
fun RecordView(records: List<Record>, viewModel: MainViewModel) {


    var showSubmit by remember { mutableStateOf(false) }
    var saveText by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }
    var hasValue by remember { mutableStateOf(false) }
    var currentId by remember { mutableStateOf("1") }
    var nextId by remember { mutableStateOf("1") }
    var currentRecord by remember { mutableStateOf<Record?>(records.find { it.id == currentId }) }

    LaunchedEffect(currentId) {
        currentRecord = records.find { it.id == currentId }
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
                            hasValue = true
                            viewModel.updateAnswer(currentId, ans)
                            ans.referTo?.id?.let { referToId ->
                                if (referToId == "submit") {
                                    showSubmit = true
                                } else {
                                    nextId = referToId
                                }
                            }?: run{
                                nextId = currentId
                            }
                        }
                    )
                }
                "numberInput" -> {
                    NumberInput(
                        record = record,
                        onValueChange = { text, error ->
                            hasValue = text!=""
                            hasError = error
                            println(hasError)
                            if(saveText){
                                viewModel.updateAnswer(
                                    questionId = record.id,
                                    answer = Answer(
                                        questionType = record.type,
                                        referTo = record.referTo,
                                        value = text,
                                        valueId = null,
                                        hasError = error
                                    )
                                )
                                saveText = false
                            }
                        }
                    )
                }
                "dropdown" -> {
                    // TODO: Handle Dropdown selector
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
                        onClick = { if(record.skip.id != "-1") currentId = record.skip.id },
                        enabled = record.skip.id != "-1"
                    ) {
                        Text("Skip")
                    }


                if (showSubmit || record.referTo?.id == "submit") {
                    Button(onClick = {

                        showSubmit = false
                    }) {
                        Text("Submit")
                    }
                } else {
                    Button(
                        onClick = {
                            val next = record.referTo?.id
                            if (!next.isNullOrEmpty()) currentId = next
                            else if (nextId != currentId) currentId = nextId

                            if (record.type.contains("input", ignoreCase = true)) saveText = true
                            hasError = false
                            hasValue = false
                        },
                        enabled = !hasError && hasValue
                    ) {
                        Text("Next")
                    }
                }
            }
        } ?: Text("Invalid question")
    }
}
