package com.hamzacanbaz.weatherapp.presentation.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hamzacanbaz.weatherapp.navigation.Destination
import com.hamzacanbaz.weatherapp.presentation.home.HomeScreen
import com.hamzacanbaz.weatherapp.presentation.splash.SplashScreen
import com.hamzacanbaz.weatherapp.presentation.splash.navigation.SplashScreenDestination

object HomeScreenDestination : Destination {
    override val route: String = "home_screen"
}

fun NavGraphBuilder.homeScreenGraph() {
    composable(route = HomeScreenDestination.route) {
        HomeScreen()
    }
}