package com.aaditx23.v2_assessment.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileUtil {
    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        return File.createTempFile(
            "JPEG_${timeStamp}_", ".jpg",
            context.filesDir
        // Internal storage:
        // manipulating this file from other apps might cause it to break
        // Pros: No need for storage permission
        )
    }

    fun getImageUri(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }

    fun deleteImageFile(context: Context, path: String) {
        val file = File(path)
        val appDir = context.filesDir.absolutePath
        if (file.exists() && file.absolutePath.startsWith(appDir)) { // validate if file is owned by this app only

            file.delete()
            println("Deleted: $path")
        }
    }
}

