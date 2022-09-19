package com.hamzacanbaz.weatherapp.presentation.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hamzacanbaz.weatherapp.navigation.Destination
import com.hamzacanbaz.weatherapp.presentation.splash.SplashScreen

object SplashScreenDestination : Destination {
    override val route: String = "splash_screen"
}

fun NavGraphBuilder.splashScreenGraph(onSplashScreenComplete: () -> Unit) {
    composable(route = SplashScreenDestination.route) {
        SplashScreen(onSplashScreenComplete = onSplashScreenComplete)
    }
}