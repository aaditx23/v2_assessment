package com.aaditx23.v2_assessment.ui.screens.main.JsonFlow

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
import com.aaditx23.v2_assessment.model.record.Record

/*

{
      "id": "1",
      "skip": {
        "id": "-1"
      },
      "type": "multipleChoice",
      "options": [
        {
          "value": "multipleChoice 1",
          "referTo": {
            "id": "2"
          }
        },
        {
          "value": "multipleChoice 2",
          "referTo": {
            "id": "3"
          }
        }
      ],
      "question": {
        "slug": "Select an option (Multiple Choice / RadioButton)"
      }
    },

*/

@Composable
fun MultipleChoice(record: Record, onSelect: (id: Int) -> Unit) {
    val options = record.options ?: return
    var selectedIndex by remember { mutableIntStateOf(-1) }

    Column {
        Text(text = record.question?.slug ?: "", style = MaterialTheme.typography.titleMedium)
        options.forEachIndexed { index, option ->
            val isSelected = selectedIndex == index
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = {
                        selectedIndex = index
                        val referToId = option.referTo?.id?.toIntOrNull() ?: -1
                        onSelect(referToId)
                    },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                )
                Text(text = option.value, modifier = Modifier)
            }
        }
    }
}