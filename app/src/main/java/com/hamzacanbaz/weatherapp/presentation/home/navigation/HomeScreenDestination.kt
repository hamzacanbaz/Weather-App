package com.hamzacanbaz.weatherapp.presentation.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hamzacanbaz.weatherapp.navigation.Destination
import com.hamzacanbaz.weatherapp.presentation.home.HomeScreen

object HomeScreenDestination : Destination {
    override val route: String = "home_screen"
}

fun NavGraphBuilder.homeScreenGraph(onClickAddLocation: () -> Unit) {
    composable(route = "${HomeScreenDestination.route}/{lat}/{lon}") {
        val lat = it.arguments?.getString("lat")
        val lon = it.arguments?.getString("lon")
        HomeScreen(
            onClickAddLocation = onClickAddLocation,
            lat = lat?.toDouble() ?: 12.0,
            lon = lon?.toDouble() ?: 23.0
        )
    }
}