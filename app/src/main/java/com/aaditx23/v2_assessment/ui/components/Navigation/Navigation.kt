package com.aaditx23.v2_assessment.ui.components.Navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aaditx23.v2_assessment.ui.screens.main.MainScreen
import com.aaditx23.v2_assessment.ui.components.Navigation.BottomNavItem.Companion.bottomNavItemList

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(){

    val navController = rememberNavController()
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry?.destination) {
        selectedIndex = when(navBackStackEntry?.destination?.route){
            "Questions" -> 0
            "Submissions" -> 1
            else -> -1
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedIndex = selectedIndex,
                onItemSelected = {
                    selectedIndex = it
                    navController.navigate(bottomNavItemList[it].title)
                }
            )
        }
    ) {

        NavHost(navController = navController, startDestination = "Questions"){

            composable("Questions") {
                MainScreen(navController = navController)
            }

            composable("Submissions") {
                Text("Submissions")
            }
        }

    }
}