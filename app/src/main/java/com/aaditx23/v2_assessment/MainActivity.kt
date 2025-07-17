package com.aaditx23.v2_assessment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.aaditx23.v2_assessment.ui.screens.Navigation
import com.aaditx23.v2_assessment.ui.theme.V2_assessmentTheme
import com.aaditx23.v2_assessment.data.local.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkMode = SharedPreferences.darkModeEnabled.collectAsState()
            BackHandler(
                enabled = true,
                onBack = {
                    // do nothing
                }
            )

            V2_assessmentTheme(
                darkTheme = darkMode.value
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
                    Navigation()
                }
            }
        }
    }
}
