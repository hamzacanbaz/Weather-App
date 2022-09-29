package com.hamzacanbaz.weatherapp.presentation

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.places.api.Places
import com.hamzacanbaz.weatherapp.presentation.addLocation.navigation.AddLocationScreenDestination
import com.hamzacanbaz.weatherapp.presentation.addLocation.navigation.addLocationScreenGraph
import com.hamzacanbaz.weatherapp.presentation.home.navigation.HomeScreenDestination
import com.hamzacanbaz.weatherapp.presentation.home.navigation.homeScreenGraph
import com.hamzacanbaz.weatherapp.presentation.splash.navigation.SplashScreenDestination
import com.hamzacanbaz.weatherapp.presentation.splash.navigation.splashScreenGraph
import com.hamzacanbaz.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val ai: ApplicationInfo = applicationContext.packageManager.getApplicationInfo(
                applicationContext.packageName,
                PackageManager.GET_META_DATA
            )
            val value = ai.metaData["com.google.android.geo.API_KEY"]
            val apiKey = value.toString()

            // Initializing the Places API
            // with the help of our API_KEY
            if (!Places.isInitialized()) {
                Places.initialize(applicationContext, apiKey)
            }

            WeatherAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = SplashScreenDestination.route
                ) {
                    splashScreenGraph(
                        onSplashScreenComplete = {
                            navController.navigate(route = "${HomeScreenDestination.route}/32.0/29.0") {
                                popUpTo(SplashScreenDestination.route) {
                                    inclusive = true
                                }
                            }
                        })
                    homeScreenGraph(
                        onClickAddLocation = {
                            navController.navigate(route = AddLocationScreenDestination.route)
                        }
                    )
                    addLocationScreenGraph(
//                        onLocationSelected = { lat: Double, lon: Double ->
//                            println("$lat $lon")
//                            navController.navigate(route = "${HomeScreenDestination.route}/$lat/$lon")
//                        }
                        onLocationSelected = {
                            // TODO dialog onaylanirsa
                            navController.navigate(route = HomeScreenDestination.route)
                        }
                    )

                }

            }
        }
    }
}
