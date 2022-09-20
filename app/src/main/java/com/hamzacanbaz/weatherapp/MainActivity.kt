package com.hamzacanbaz.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
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
            WeatherAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = SplashScreenDestination.route
                ) {
                    splashScreenGraph(
                        onSplashScreenComplete = {
                            navController.navigate(route = HomeScreenDestination.route) {
                                popUpTo(SplashScreenDestination.route) {
                                    inclusive = true
                                }
                            }
                        })
                    homeScreenGraph()
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        Greeting("Android")
    }
}