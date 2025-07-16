package com.aaditx23.v2_assessment.ui.screens.main.JsonFlow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.unit.dp
import com.aaditx23.v2_assessment.model.Answer
import com.aaditx23.v2_assessment.model.record.Record
import java.util.regex.Pattern

@Composable
fun NumberInput(
    record: Record,
    onValueChange: (Answer) -> Unit,
) {
    var input by remember { mutableStateOf("") }
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
        Text(text = record.question?.slug ?: "Question not Found")
        OutlinedTextField(
            value = input,
            onValueChange = {
                input = it
                if (regex != null && it.isNotBlank()) {
                    val pattern = Pattern.compile(regex)
                    error = if (!pattern.matcher(it).matches()) "Invalid input" else null
                } else {
                    error = null
                }
                onValueChange(ans.copy(value = it, hasError = error != null) )
            },
            isError = error != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            label = { Text("Number") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        )
        if (error != null) {
            Text(text = error!!, color = MaterialTheme.colorScheme.error)
        }
    }
}