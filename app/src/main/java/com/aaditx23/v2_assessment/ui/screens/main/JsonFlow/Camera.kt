package com.aaditx23.v2_assessment.ui.screens.main.JsonFlow

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.aaditx23.v2_assessment.model.answer.Answer
import com.aaditx23.v2_assessment.util.CameraPermission
import com.aaditx23.v2_assessment.util.FileUtil
import com.aaditx23.v2_assessment.model.record.Record

@Composable
fun Camera(record: Record, onSave: (Answer) -> Unit) {
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
            println("IMAGE PATH: \n$imagePath")
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
            .fillMaxSize(),
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
        } else {
            Text("Launching camera...")
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
