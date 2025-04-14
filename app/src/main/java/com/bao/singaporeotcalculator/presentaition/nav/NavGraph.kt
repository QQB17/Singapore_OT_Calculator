package com.bao.singaporeotcalculator.presentaition.nav

sealed class Screen(val route: String){
    data object Home: Screen("home")
    data object Settings: Screen("settings")
}