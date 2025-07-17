package com.aaditx23.v2_assessment.ui.components.Navigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.AssignmentTurnedIn
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    object Questions: BottomNavItem(
        selectedIcon = Icons.Filled.Quiz,
        unselectedIcon = Icons.Outlined.Quiz,
        title = "Questions",
    )
    object Submissions: BottomNavItem(
        selectedIcon = Icons.Filled.AssignmentTurnedIn,
        unselectedIcon = Icons.Outlined.AssignmentTurnedIn,
        title = "Submissions",
    )

    companion object{
        val bottomNavItemList = listOf(
            Questions,
            Submissions
        )

    }



}