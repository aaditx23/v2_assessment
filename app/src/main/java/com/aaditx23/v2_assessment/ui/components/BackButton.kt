package com.aaditx23.v2_assessment.ui.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun BackButton(
    onClick: () -> Unit,
    icon: ImageVector = Icons.Filled.ArrowBackIosNew
) {
    // Remember the offset to allow dragging
    val offsetX = rememberSaveable { mutableFloatStateOf(0f) }
    val offsetY = rememberSaveable { mutableFloatStateOf(0f) }

    // Box to hold the button
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 20.dp, bottom = 50.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        // Small Floating Action Button with drag functionality
        FloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .offset { IntOffset(offsetX.floatValue.roundToInt(), offsetY.floatValue.roundToInt()) }
                .align(Alignment.BottomEnd)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX.floatValue += dragAmount.x
                        offsetY.floatValue += dragAmount.y
                    }
                },
            shape = CircleShape
        ) {
            Icon(icon, contentDescription = "Small floating action button.")
        }
    }
}