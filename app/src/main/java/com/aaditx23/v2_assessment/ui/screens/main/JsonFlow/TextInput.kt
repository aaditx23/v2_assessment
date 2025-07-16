package com.aaditx23.v2_assessment.ui.screens.main.JsonFlow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aaditx23.v2_assessment.model.Answer
import com.aaditx23.v2_assessment.model.record.Record
import java.util.regex.Pattern

@Composable
fun TextInput(record: Record, onChange: (Answer) -> Unit) {
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
            value = text,
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
        )
        if (error != null) {
            Text(error!!, color = Color.Red)
        }
    }
}