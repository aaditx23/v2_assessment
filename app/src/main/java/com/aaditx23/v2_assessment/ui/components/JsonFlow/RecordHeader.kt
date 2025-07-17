package com.aaditx23.v2_assessment.ui.components.JsonFlow

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.aaditx23.v2_assessment.ui.components.DarkModeToggle
import com.aaditx23.v2_assessment.ui.components.ProgressBar
import kotlin.math.roundToInt

@Composable
fun RecordHeader(
    currentId: Float, size: Float,
    onRestart: () -> Unit
){

    val offsetX = rememberSaveable { mutableFloatStateOf(0f) }
    val offsetY = rememberSaveable { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
        ,
        contentAlignment = Alignment.TopEnd,

    ) {
        ProgressBar(currentId, size)
        Text(
            text = "Questions",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 16.dp)
                .wrapContentSize(align = Alignment.Center)
                .offset { IntOffset(offsetX.floatValue.roundToInt(), offsetY.floatValue.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX.floatValue += dragAmount.x
                        offsetY.floatValue += dragAmount.y
                    }
                },
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.12f)
            ){
                DarkModeToggle()
                IconButton(
                    onClick = onRestart,
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
}