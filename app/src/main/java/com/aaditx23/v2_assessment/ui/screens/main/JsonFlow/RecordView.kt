package com.aaditx23.v2_assessment.ui.screens.main.JsonFlow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aaditx23.v2_assessment.model.record.Record

@Composable
fun RecordView(records: List<Record>){
    fun findRecord(id: Int): Record?{
        records.forEach {
            if (it.id == id) return it
        }
        return null
    }

    var showSubmit by remember { mutableStateOf(false) }
    var skippable by remember { mutableStateOf(false) }
    var currentIndex by remember { mutableIntStateOf(1) }
    var currentRecord by remember {mutableStateOf<Record?>(records[1])}

    LaunchedEffect(currentIndex) {
        currentRecord = findRecord(currentIndex)
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        println(currentRecord)

        item{

            currentRecord?.let {
                when (it.type) {
                    "multipleChoice" -> {
                        MultipleChoice(
                            record = currentRecord!!,
                            onSelect = { id ->
//                                if(id > -1) currentIndex = id
                            }
                        )
                    }

                    "numberInput" -> {
                        // TODO: Handle Number input field
                    }

                    "dropdown" -> {
                        // TODO: Handle Dropdown selector
                    }

                    "checkbox" -> {
                        // TODO: Handle Checkboxes, multi-select
                    }

                    "camera" -> {
                        // TODO: Handle Capture photo using camera
                    }

                    "textInput" -> {
                        // TODO: Handle Text input field
                    }

                    else -> {
                        // TODO: Handle unknown type
                    }
                }
            } ?: run {  }


        }
        item{
            Row {
                Button(
                    onClick = {
                        currentIndex++
                    },
                    enabled = (currentRecord != null && currentRecord!!.skip.id.toInt() > 0)
                ) {
                    Text("Skip")
                }

                Button(
                    onClick = {
                        currentIndex++
                    }
                ) {
                    Text("Next")
                }
            }
        }
    }
}