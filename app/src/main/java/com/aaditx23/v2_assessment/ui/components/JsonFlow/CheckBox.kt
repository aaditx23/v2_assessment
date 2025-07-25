package com.aaditx23.v2_assessment.ui.components.JsonFlow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aaditx23.v2_assessment.model.Answer
import com.aaditx23.v2_assessment.model.record.Record

@Composable
fun CheckBox(record: Record, onSave: (Answer) -> Unit, answer: Answer? = null) {
    val options = record.options ?: emptyList()
    var checkedStates by remember { mutableStateOf(List(options.size) { false }) }
    val answerValues = answer?.value?.split("|") ?: emptyList<String>()
    var ans by remember {
        mutableStateOf(
            Answer(
                questionType = record.type,
                referTo = record.referTo,
                value = "",
                valueId = null,
                hasError = false
            )
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentHeight()
    ) {
        Text(
            text = record.question?.slug ?: "",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        options.forEachIndexed { index, option ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = if (answer == null) checkedStates[index] else answerValues.contains(option.value),
                    onCheckedChange = { checked ->
                        checkedStates = checkedStates.toMutableList().also { it[index] = checked }
                        val selected = options
                            .mapIndexedNotNull { i, opt -> if (checkedStates[i]) opt.value else null }
                        val joined = selected.joinToString("|")
                        ans = ans.copy(value = joined)
                        onSave(ans)
                    },
                    enabled = answer == null
                )
                Text(
                    text = option.value,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

    }
}