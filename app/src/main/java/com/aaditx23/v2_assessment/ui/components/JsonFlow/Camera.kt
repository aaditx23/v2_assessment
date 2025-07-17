package com.aaditx23.v2_assessment.ui.components.JsonFlow

import android.Manifest
import android.app.Activity
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil3.compose.AsyncImage
import com.aaditx23.v2_assessment.model.Answer
import com.aaditx23.v2_assessment.util.FileUtil
import com.aaditx23.v2_assessment.model.record.Record
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Camera(
    record: Record,
    onSave: (Answer) -> Unit,
    answer: Answer? = null
) {
    if (answer != null) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Captured Image:", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            AsyncImage(
                model = answer.value,
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).padding(20.dp)
            )
        }
    } else {
        val context = LocalContext.current
        var imageUri by remember { mutableStateOf<Uri?>(null) }
        var imagePath by remember { mutableStateOf("") }

        val permissionState = rememberPermissionState(
            permission = Manifest.permission.CAMERA
        )
        var isRejected by remember { mutableStateOf(false) }

        val lifeCycleOwner = LocalLifecycleOwner.current
        DisposableEffect(
            key1 = lifeCycleOwner,
            effect = {
                val observer = LifecycleEventObserver{ _, event ->
                    if(event == Lifecycle.Event.ON_START){
                        permissionState.launchPermissionRequest()
                    }
                }
                lifeCycleOwner.lifecycle.addObserver(observer)

                onDispose {
                    lifeCycleOwner.lifecycle.removeObserver(observer)
                }
            }
        )


        val ans = Answer(
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
            } else isRejected = true
        }

        Box(
            modifier = Modifier
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            when  {
                permissionState.status.isGranted -> {
                    LaunchedEffect(Unit) {
                        launchCamera(context, cameraLauncher, { imagePath = it }, { imageUri = it })
                    }
                }
                permissionState.status.shouldShowRationale -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Camera permission is required to proceed.")
                        Button(onClick = { permissionState.launchPermissionRequest() }) {
                            Text("Request Permission")
                        }
                    }
                }
                !permissionState.status.isGranted && !permissionState.status.shouldShowRationale -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Camera permission permanently denied. Please enable it in app settings.")
                        val activity = context as? Activity
                        if (activity != null) {
                            Button(onClick = { openAppSettings(activity) }) {
                                Text("Open App Settings")
                            }
                        }
                    }
                }
            }
            if (isRejected) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Picture was cancelled or failed.")
                    Button(onClick = {
                        if (permissionState.status is PermissionStatus.Granted) {
                            launchCamera(context, cameraLauncher, { imagePath = it }, { imageUri = it })
                            isRejected = false
                        }
                    }) {
                        Text("Try Again")
                    }
                }
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

private fun openAppSettings(activity: Activity) {
    val intent =
        android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = android.net.Uri.fromParts("package", activity.packageName, null)
    intent.data = uri
    activity.startActivity(intent)

}