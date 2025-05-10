package com.bao.singaporeotcalculator.presentaition.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bao.singaporeotcalculator.presentaition.ui.HomeScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        startDestination = HomeScreen.Landing.route,
        route = Screen.Home.route
    ) {
        composable(route = HomeScreen.Landing.route) {
            HomeScreen(navController = navController)
        }
    }
}