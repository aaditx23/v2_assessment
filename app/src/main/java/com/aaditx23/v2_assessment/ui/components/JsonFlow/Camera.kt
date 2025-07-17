package com.aaditx23.v2_assessment.ui.components.JsonFlow

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.aaditx23.v2_assessment.model.Answer
import com.aaditx23.v2_assessment.model.record.Record
import com.aaditx23.v2_assessment.util.CameraPermission
import com.aaditx23.v2_assessment.util.FileUtil


@Composable
fun Camera(record: Record, onSave: (Answer) -> Unit, answer: Answer? = null) {
    answer?.let {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Captured Image:",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            AsyncImage(
                model = it.value,
                contentDescription = "Image ",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .padding(20.dp)
            )
        }
    }?: run{
        val context = LocalContext.current
        var imageUri by remember { mutableStateOf<Uri?>(null) }
        var imagePath by remember { mutableStateOf("") }
        var permissionRequested by remember { mutableStateOf(false) }
        var isRejected by remember { mutableStateOf(false) }
        var ans = Answer(
            questionType = record.type,
            referTo = record.referTo,
            value = "",
            valueId = null,
            hasError = false
        )

        val cameraLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->
            if (success && imagePath.isNotEmpty()) {
                onSave(ans.copy(value = imagePath))
                isRejected = false
            } else {
                isRejected = true
            }
        }

        val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) launchCamera(context, cameraLauncher, { imagePath = it }, { imageUri = it })
        }

        LaunchedEffect(Unit) {
            if (!CameraPermission.hasCameraPermission(context) && !permissionRequested) {
                permissionRequested = true
                permissionLauncher.launch(CameraPermission.CAMERA_PERMISSION)
            } else if (CameraPermission.hasCameraPermission(context)) {
                launchCamera(context, cameraLauncher, { imagePath = it }, { imageUri = it })
            }
        }

        Box(
            modifier = Modifier
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ){
            if (isRejected) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Picture was rejected or cancelled.")
                    Button(onClick = {
                        isRejected = false
                        launchCamera(context, cameraLauncher, { imagePath = it }, { imageUri = it })
                    }) {
                        Text("Try Again")
                    }
                }
            }
            else {
                Text("Launching camera...")
            }

        }
    }
}

private fun launchCamera(
    context: Context,
    cameraLauncher: ActivityResultLauncher<Uri>,
    setPath: (String) -> Unit,
    setUri: (Uri) -> Unit
) {
    val file = FileUtil.createImageFile(context)
    setPath(file.absolutePath)
    val uri = FileUtil.getImageUri(context, file)
    setUri(uri)
    cameraLauncher.launch(uri)
}
