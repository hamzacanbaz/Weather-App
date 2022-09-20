package com.hamzacanbaz.weatherapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hamzacanbaz.weatherapp.R
import com.hamzacanbaz.weatherapp.domain.model.CurrentWeatherModel
import com.hamzacanbaz.weatherapp.ui.theme.CardBackground
import com.hamzacanbaz.weatherapp.ui.theme.DateColor
import com.hamzacanbaz.weatherapp.ui.theme.GradientDark
import com.hamzacanbaz.weatherapp.ui.theme.GradientLight
import com.hamzacanbaz.weatherapp.util.Constants
import com.hamzacanbaz.weatherapp.util.Resource

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val currentWeather by homeViewModel.getCurrentWeather()
    val weatherForecast by homeViewModel.getForecast()
    val time by homeViewModel.getDate()
    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = currentWeather is Resource.Loading)
    val scrollState = rememberScrollState()

    when (currentWeather) {
        is Resource.Success -> {
            val viewState = (currentWeather as Resource.Success<CurrentWeatherModel>).data
            Surface() {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    GradientDark, GradientLight
                                )
                            )
                        )
                        .fillMaxSize()
                ) {
                    SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = {
                            if (viewState != null) {
                                homeViewModel.getWeatherCurrent(
                                    viewState.lat,
                                    viewState.lon
                                )
                                homeViewModel.getWeatherForecast(
                                    viewState.lat,
                                    viewState.lon
                                )
                            }
                        },
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(scrollState)
                        ) {
                            Location(viewState!!.locationName)
                            Date(time)
                            DegreeAndIconPart(
                                viewState.mainTemp,
                                viewState.weatherIcon
                            )
                            MinMaxAndFeelingPart(
                                viewState.mainTempMin,
                                viewState.mainTempMax,
                                viewState.mainFeelsLike
                            )
                            Weather(viewState.weatherDesc)
                            WeatherDetails(
                                viewState.mainHumidity,
                                viewState.windSpeed
                            )

                            Text(
                                text = "Hourly",
                                modifier = Modifier.padding(start = 16.dp, top = 36.dp),
                                fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp
                            )

                            LazyRow {
                                items(weatherForecast.data ?: listOf()) {
                                    NextForecastItem(it.temp, it.time, it.date, it.icon)
                                }
                            }
                        }
                    }
                }
            }
        }
        is Resource.Error -> {
            println(currentWeather.errorMessage)
            println("error")
        }
        is Resource.Loading -> {
            println("loading")
            LoadingScreen()
        }
    }

    LaunchedEffect(Unit) {
        homeViewModel.getWeatherCurrent(Constants.LAT, Constants.LON)
        homeViewModel.getWeatherForecast(Constants.LAT, Constants.LON)
    }
}

@Composable
fun DegreeAndIconPart(currentTemp: String, iconSrc: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(top = 36.dp)
            .fillMaxWidth()
    ) {
        Image(
            painterResource(iconSrc),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(78.dp)
        )
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            NormalText(text = "$currentTemp°", fontSize = 64.sp)

        }
    }


}


@Composable
fun MinMaxAndFeelingPart(minTemp: String, maxTemp: String, feelingTemp: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)

    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            NormalText(text = "$minTemp°/$maxTemp°")
        }
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            NormalText(text = "Feels like $feelingTemp°")
        }
    }


}

@Composable
fun Location(locationName: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp)
    ) {
        Icon(Icons.Filled.LocationOn, tint = Color.White, contentDescription = "location")   // ok
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                color = Color.White,
                text = locationName,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun Date(date: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 16.dp, top = 4.dp),
            contentAlignment = Alignment.Center,
        ) {
            NormalText(text = date, color = DateColor, fontSize = 16.sp)
        }

    }


}

@Composable
fun Weather(weather: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize(),
            contentAlignment = Alignment.Center,
        ) {
            NormalText(text = weather, fontWeight = FontWeight.Bold)
        }

    }


}

@Composable
fun WeatherDetails(humidity: String, windSpeed: String) {
    Box(
        modifier = Modifier
            .padding(top = 32.dp, start = 12.dp, end = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardBackground)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 32.dp, bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceAround

        ) {
            weatherDetailItem(
                iconId = R.drawable.water,
                contentDescription = "water",
                itemName = "Humidity",
                itemValue = "$humidity%"
            )
//            Divider(
//                color = Color.White,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(1.dp)
//            )
            weatherDetailItem(R.drawable.wind, "wind", "Wind Speed", "$windSpeed m/s")
        }
    }
}

@Composable
fun weatherDetailItem(
    iconId: Int,
    contentDescription: String,
    itemName: String,
    itemValue: String
) {
    Row(Modifier.height(IntrinsicSize.Min)) {
        Box(
            modifier = Modifier
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painterResource(iconId),
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(24.dp)
            )
        }
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                color = Color.White,
                text = itemName,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                color = Color.White,
                text = itemValue,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun NextForecastItem(temp: String, time: String, date: String, iconSrc: Int) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardBackground), contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .wrapContentHeight()
                .width(IntrinsicSize.Min)
                .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    color = Color.White,
                    text = time,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    color = Color.White,
                    text = date,
                    fontSize = 8.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painterResource(iconSrc),
                    contentDescription = "contentDescription",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    color = Color.White,
                    text = temp,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            GradientDark, GradientLight
                        )
                    )
                )
                .fillMaxSize()
        ) {
            LoadingAnimation(modifier = Modifier.size(75.dp))
        }
    }
}

@Composable
fun LoadingAnimation(modifier: Modifier = Modifier) {
    Card(modifier = modifier, elevation = 0.dp) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                R.raw.loadinganimation
            )
        )
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}

@Composable
fun NormalText(
    text: String,
    fontSize: TextUnit = 20.sp,
    color: Color = Color.White,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        fontWeight = fontWeight,
        color = color,
        text = text,
        fontSize = fontSize,
        textAlign = TextAlign.Center
    )
}