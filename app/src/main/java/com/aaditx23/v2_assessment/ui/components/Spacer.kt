package com.aaditx23.v2_assessment.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HSpace(dp: Int){
    Spacer(modifier = Modifier.width(dp.dp))
}

@Composable
fun VSpace(dp: Int){
    Spacer(modifier = Modifier.height(dp.dp))
}