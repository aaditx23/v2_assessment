package com.aaditx23.v2_assessment.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(message: String, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = onCancel) {
                Text("Refresh")
            }
        },
        title = {
            Text("Error")
        },
        text = {
            Text(message)
        }
    )
}
