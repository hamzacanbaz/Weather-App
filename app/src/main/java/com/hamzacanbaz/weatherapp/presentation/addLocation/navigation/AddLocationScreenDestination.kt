package com.hamzacanbaz.weatherapp.presentation.addLocation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hamzacanbaz.weatherapp.navigation.Destination
import com.hamzacanbaz.weatherapp.presentation.addLocation.AddLocationScreen

object AddLocationScreenDestination : Destination {
    override val route: String = "add_location_screen"
}

fun NavGraphBuilder.addLocationScreenGraph(onLocationSelected: () -> Unit) {
    composable(route = AddLocationScreenDestination.route ) {
        AddLocationScreen(onLocationSelected = onLocationSelected)
    }
}