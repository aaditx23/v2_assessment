package com.aaditx23.v2_assessment.ui.screens.main.JsonFlow

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aaditx23.v2_assessment.model.Answer
import com.aaditx23.v2_assessment.model.record.Record
import com.aaditx23.v2_assessment.model.record.ReferId
import com.aaditx23.v2_assessment.ui.components.DropDown.DropDownItem
import com.aaditx23.v2_assessment.ui.components.DropDown.ExpandingDropDown

@Composable
fun DropDown(
    record: Record,
    onSelect: (Answer) -> Unit
) {
    val options = record.options?.map { DropDownItem(name = it.value, referId = it.referTo) } ?: emptyList()
    var selectedOption by remember { mutableStateOf("Select an option") }
    var ans = Answer(
        questionType = record.type,
        referTo = null,
        value = "",
        valueId = null,
        hasError = false
    )

    ExpandingDropDown(
        label = record.question?.slug ?: "Invalid Label",
        onSelected = {
            selectedOption = it.name
            onSelect(
                ans.copy(referTo = it.referId, value = it.name)
            )
        },
        list = options,
        selected = selectedOption,
        modifier = Modifier
            .padding(10.dp)
    )
}