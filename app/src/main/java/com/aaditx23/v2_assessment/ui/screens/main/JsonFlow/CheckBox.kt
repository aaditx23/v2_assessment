package com.aaditx23.v2_assessment.ui.screens.main.JsonFlow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aaditx23.v2_assessment.model.Answer
import com.aaditx23.v2_assessment.model.record.Record

@Composable
fun CheckBox(record: Record, onSave: (Answer) -> Unit) {
    val options = record.options ?: emptyList()
    var checkedStates by remember { mutableStateOf(List(options.size) { false }) }
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

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(
                text = record.question?.slug ?: "",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        itemsIndexed(options) { index, option ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkedStates[index],
                    onCheckedChange = { checked ->
                        checkedStates = checkedStates.toMutableList().also { it[index] = checked }
                        val selected = options
                            .mapIndexedNotNull { i, opt -> if (checkedStates[i]) opt.value else null }
                        val joined = selected.joinToString("|")
                        ans = ans.copy(value = joined)
                        onSave(ans)
                    }
                )
                Text(
                    text = option.value,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

    }
}