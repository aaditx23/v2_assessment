package com.aaditx23.v2_assessment.ui.components.JsonFlow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import com.aaditx23.v2_assessment.model.Answer
import com.aaditx23.v2_assessment.model.record.Record

@Composable
fun MultipleChoice(record: Record, onSelect: (Answer) -> Unit, answer: Answer? = null) {
    val options = record.options ?: return
    var selectedIndex by remember { mutableIntStateOf(-1) }

    Column {
        Text(text = record.question?.slug ?: "", style = MaterialTheme.typography.titleMedium)
        options.forEachIndexed { index, option ->
            val isSelected = if(answer == null) selectedIndex == index else answer.valueId?.toInt() == index
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = {
                        selectedIndex = index
                        onSelect(
                            Answer(
                                questionType = record.type,
                                referTo = option.referTo,
                                value = option.value,
                                valueId = index.toString()
                            )
                        )
                    },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary),
                    enabled = answer == null
                )
                Text(
                    text = option.value,
                    modifier = Modifier
                )
            }
        }
    }
}