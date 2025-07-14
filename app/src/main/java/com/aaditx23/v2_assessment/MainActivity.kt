package com.aaditx23.v2_assessment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aaditx23.v2_assessment.ui.screens.main.MainScreen
import com.aaditx23.v2_assessment.ui.theme.V2_assessmentTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.Properties


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            V2_assessmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}
