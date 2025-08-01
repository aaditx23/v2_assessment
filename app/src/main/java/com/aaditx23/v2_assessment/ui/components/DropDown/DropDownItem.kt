package com.aaditx23.v2_assessment.ui.components.DropDown

import androidx.compose.ui.graphics.vector.ImageVector
import com.aaditx23.v2_assessment.model.record.ReferId

data class DropDownItem(
    val name: String,
    val icon: ImageVector? = null,
    val referId: ReferId? = null
)