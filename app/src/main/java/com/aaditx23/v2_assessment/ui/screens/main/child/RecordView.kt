package com.aaditx23.v2_assessment.ui.screens.main.child

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aaditx23.v2_assessment.MainActivity
import com.aaditx23.v2_assessment.model.Answer
import com.aaditx23.v2_assessment.model.record.Record
import com.aaditx23.v2_assessment.ui.components.DarkModeToggle
import com.aaditx23.v2_assessment.ui.components.JsonFlow.Camera
import com.aaditx23.v2_assessment.ui.components.JsonFlow.CheckBox
import com.aaditx23.v2_assessment.ui.components.JsonFlow.DropDown
import com.aaditx23.v2_assessment.ui.components.JsonFlow.MultipleChoice
import com.aaditx23.v2_assessment.ui.components.JsonFlow.NumberInput
import com.aaditx23.v2_assessment.ui.components.JsonFlow.TextInput
import com.aaditx23.v2_assessment.ui.screens.main.MainViewModel
import com.aaditx23.v2_assessment.data.local.SharedPreferences
import com.aaditx23.v2_assessment.ui.components.ProgressBar

@Composable
fun RecordView(records: List<Record>, viewModel: MainViewModel) {
    val uiState by viewModel.recordUiState.collectAsState()
    val submitted by SharedPreferences.submitted.collectAsState()

    var currentRecord by remember { mutableStateOf<Record?>(records.find { it.id == uiState.currentId }) }
    val context = LocalContext.current

    LaunchedEffect(uiState.currentId) {
        currentRecord = records.find { it.id == uiState.currentId }
        viewModel.resetErrorAndHasValue()
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        ProgressBar(uiState.currentId.toFloat(), records.size.toFloat())

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ),
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 16.dp)
                .wrapContentSize(align = Alignment.Center)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.12f)
            ){
                DarkModeToggle()
                IconButton(
                    onClick = { viewModel.restart() },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = "Refresh",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
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
                    Camera(
                        record = record,
                        onSave = {
                            viewModel.updateValueErrorAndNextId(it)
                            viewModel.updateAnswer(record.id, it, record.referTo)
                        }
                    )
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
                    Button(
                        onClick = {
                            println("Current Answer $currentAnswer")
                            currentAnswer?.let {
                                viewModel.submitAnswer(
                                    questionId = uiState.currentId,
                                    answer = it,
                                )
                                SharedPreferences.setSubmitted(context, true)
                            }
                        },
                        enabled = !uiState.hasError && uiState.hasValue
                    ) {
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
                        },
                        enabled = !uiState.hasError && uiState.hasValue
                    ) {
                        Text("Next")
                    }
                }
            }
        } ?: Text("Invalid question")

        if(submitted){
            LaunchedEffect(Unit){
                SharedPreferences.setSubmitted(context, true)
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                (context as? Activity)?.finish()
            }
        }
    }
}
