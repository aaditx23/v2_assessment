package com.aaditx23.v2_assessment.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aaditx23.v2_assessment.data.local.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun DarkModeToggle(

) {
    val context = LocalContext.current
    var darkMode = SharedPreferences.darkModeEnabled.collectAsState()
    Switch(
        checked = darkMode.value,
        onCheckedChange = {
            SharedPreferences.setDarkModeEnabled(context, !darkMode.value)
        },
        colors = SwitchDefaults.colors(MaterialTheme.colorScheme.primary),
        thumbContent = {
            if(darkMode.value){
                GradientBGButton(
                    bgColor = GradientColors.MoonGradient,
                    imageVector = Icons.Filled.DarkMode,
                    contentDescription = "Dark Mode",
                    tint = Color.White
                )
            }
            else{
                GradientBGButton(
                    bgColor = GradientColors.SunGradient,
                    imageVector = Icons.Filled.LightMode,
                    contentDescription = "Light Mode",
                    tint = Color.Yellow
                )
            }
        },
        modifier = Modifier.scale(0.7f)


    )
}

@Composable
fun GradientBGButton(
    bgColor: Brush,
    imageVector: ImageVector,
    contentDescription: String,
    tint: Color
){
    Box(
        modifier = Modifier
            .scale(01f)
            .clip(CircleShape)
            .background(bgColor),
        contentAlignment = Alignment.Center
    ){
        Image(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(20.dp),
            colorFilter = ColorFilter.tint(
                color = tint
            )
        )
    }
}