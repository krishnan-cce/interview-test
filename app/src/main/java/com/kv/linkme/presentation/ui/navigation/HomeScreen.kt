package com.kv.linkme.presentation.ui.navigation

sealed class HomeScreen(val route: String) {
    data object SplashScreen : HomeScreen("SplashScreen")
    data object UsersScreen : HomeScreen("UsersScreen")

}