package com.aaditx23.v2_assessment.ui.components.JsonFlow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aaditx23.v2_assessment.model.Answer
import com.aaditx23.v2_assessment.model.record.Record
import java.util.regex.Pattern

@Composable
fun TextInput(record: Record, onChange: (Answer) -> Unit, answer: Answer? = null) {
    var text by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val regex = record.validations?.regex
    val ans = Answer(
        questionType = record.type,
        referTo = record.referTo,
        value = "",
        valueId = null,
        hasError = false
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(text = record.question?.slug ?: "Enter text")
        OutlinedTextField(
            value = answer?.value ?: text,
            onValueChange = {
                text = it
                if (regex != null && it.isNotBlank()) {
                    try {
                        val pattern = Pattern.compile(regex)
                        error = if (!pattern.matcher(it).matches()) "Only letters are allowed" else null
                    } catch (e: Exception) {
                        error = "Invalid regex"
                    }
                } else {
                    error = null
                }
                onChange(ans.copy(value = it, hasError = error != null))
            },
            isError = error != null,
            label = { Text("Enter text") },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth(),
            enabled = answer == null
        )
        if (error != null) {
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }
    }
}