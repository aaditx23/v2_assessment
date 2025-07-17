package com.aaditx23.v2_assessment.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aaditx23.v2_assessment.ui.components.BottomNav.BottomNavBar
import com.aaditx23.v2_assessment.ui.components.BottomNav.BottomNavItem.Companion.bottomNavItemList
import com.aaditx23.v2_assessment.ui.screens.answer.AnswerScreen
import com.aaditx23.v2_assessment.ui.screens.main.MainScreen
import com.aaditx23.v2_assessment.ui.screens.submitted.SubmittedScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(){

    val navController = rememberNavController()
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    LaunchedEffect(navBackStackEntry?.destination) {
        selectedIndex = when(navBackStackEntry?.destination?.route){
            "Questions" -> 0
            "Submissions" -> 1
            else -> -1
        }
    }

    Scaffold(
        bottomBar = {
            if(currentRoute != null && !currentRoute.contains("answer")){
                BottomNavBar(
                    selectedIndex = selectedIndex,
                    onItemSelected = {
                        selectedIndex = it
                        navController.navigate(bottomNavItemList[it].title)
                    }
                )
            }

        }
    ) {

        NavHost(navController = navController, startDestination = "Questions"){

            composable("Questions") {
                MainScreen(navController = navController)
            }

            composable("Submissions"){
                SubmittedScreen(navController = navController)
            }

            composable(
                "answer/{submissionId}",
                arguments = listOf(navArgument("submissionId") {type = NavType.LongType})
            ) { backStackEntry ->
                backStackEntry.arguments?.let{
                    val submissionId = it.getLong("submissionId")
                    AnswerScreen(submissionId = submissionId, navController = navController)
                }

            }
        }

    }
}

