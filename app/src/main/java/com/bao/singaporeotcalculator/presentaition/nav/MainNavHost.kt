package com.bao.singaporeotcalculator.presentaition.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MainNavHost(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        homeNavGraph(navController = navController)
    }
}

sealed class HomeScreen(val route: String) {
    data object Landing : HomeScreen("home_landing")
}