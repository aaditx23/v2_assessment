package com.aaditx23.v2_assessment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.aaditx23.v2_assessment.ui.screens.Navigation
import com.aaditx23.v2_assessment.ui.theme.V2_assessmentTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            V2_assessmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
                    Navigation()
                }
            }
        }
    }
}
