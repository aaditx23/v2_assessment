package com.aaditx23.v2_assessment.ui.components.JsonFlow

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SubmitSuccessDialog(
    onViewSubmissions: () -> Unit,
    onRestart: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Submission successful") },
        text = null,
        confirmButton = {
            TextButton(onClick = onViewSubmissions) {
                Text("View submissions")
            }
        },
        dismissButton = {
            TextButton(onClick = onRestart) {
                Text("Restart")
            }
        },
        modifier = Modifier
            .wrapContentHeight()
    )
}
