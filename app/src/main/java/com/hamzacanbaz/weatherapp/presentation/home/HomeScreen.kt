package com.hamzacanbaz.weatherapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.hamzacanbaz.weatherapp.ui.theme.CardBackground
import com.hamzacanbaz.weatherapp.ui.theme.DateColor
import com.hamzacanbaz.weatherapp.ui.theme.GradientDark
import com.hamzacanbaz.weatherapp.ui.theme.GradientLight
import com.hamzacanbaz.weatherapp.util.Resource

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val currentWeather by homeViewModel.getCurrentWeather()
    val weatherForecast by homeViewModel.getForecast()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = currentWeather is Resource.Loading)

    val scrollState = rememberScrollState()

    when (currentWeather) {
        is Resource.Success -> {
            println("success")
            val viewState = (currentWeather as Resource.Success<HomeScreenViewState>).data
            Surface(

            ) {
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
                                homeViewModel.getCurrentWeather(
                                    viewState.currentWeatherResponse.coord.lat,
                                    viewState.currentWeatherResponse.coord.lon
                                )
                                homeViewModel.getWeatherForecast(
                                    viewState.currentWeatherResponse.coord.lat,
                                    viewState.currentWeatherResponse.coord.lon

                                )
                            }
                        },
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(scrollState)
                        ) {
                            Location()
                            Date()
                            DegreeAndIconPart()
                            MinMaxAndFeelingPart()
                            Weather()
                            WeatherDetails()
//                        PriceHeader(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
//                            currency = stringResource(id = R.string.bitcoin_btc),
//                            price = viewState.marketInformation.currentPrice,
//                            changeRate = viewState.marketInformation.changeRate,
//                            isChangeRatePositive = viewState.isChangeStatusPositive()
//                        )
//
//                        TimeRangePicker(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
//                            selectedTimeRange = viewState.getTimeRange()
//                        ) { timeRange ->
//                            marketViewModel.getMarketInformation(timeRange)
//                        }
//
//                        Chart(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(top = 8.dp),
//                            lineDataSet = viewState.getLineDataSet(LocalContext.current),
//                        )
//
//                        Price(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
//                            openPrice = viewState.marketInformation.openPrice,
//                            closePrice = viewState.marketInformation.closePrice,
//                            highPrice = viewState.marketInformation.highPrice,
//                            lowPrice = viewState.marketInformation.lowPrice,
//                            averagePrice = viewState.marketInformation.averagePrice,
//                            changePrice = viewState.marketInformation.changePrice
//                        )
//
//                        AboutChart(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(start = 16.dp, top = 24.dp, end = 16.dp),
//                            aboutChart = viewState.marketInformation.aboutChart
//                        )
                        }
                    }

                }
            }
        }
        is Resource.Error -> {
            println(currentWeather.errorMessage)
            println("error")
//            ErrorScreen(errorScreenViewState = ErrorScreenViewState((uiState as UiState.Error).exception)) {
//                marketViewModel.getMarketInformation(TimeRange.THIRTY_DAYS)
//            }
        }
        is Resource.Loading -> {
            println("loading")

            LoadingScreen()
        }
        else -> {}
    }

    LaunchedEffect(Unit) {
        homeViewModel.getCurrentWeather(37.0, 28.0)
        homeViewModel.getWeatherForecast(37.0, 28.0)
    }


}

@Composable
fun DegreeAndIconPart() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(top = 36.dp)
            .fillMaxWidth()


    ) {
        Image(
            painterResource(R.drawable.sun),
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
            NormalText(text = "23°", fontSize = 64.sp)

        }
    }


}


@Composable
fun MinMaxAndFeelingPart() {
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
            NormalText(text = "21°/27°")
        }

        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            NormalText(text = "Feels like 25°")
        }
    }


}

@Composable
fun Location() {
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
                text = "London",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun Date() {
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
            NormalText(text = "Thu 5 December 8:41 am", color = DateColor, fontSize = 16.sp)
        }

    }


}

@Composable
fun Weather() {
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
            NormalText(text = "Cloudy", fontWeight = FontWeight.Bold)
        }

    }


}

@Composable
fun WeatherDetails() {
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
                itemValue = "20%"
            )

//            Divider(
//                color = Color.White,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(1.dp)
//            )
            weatherDetailItem(R.drawable.wind, "wind", "Wind Speed", "0.62 m/s%")
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
                textAlign = TextAlign.Center
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
fun LoadingScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
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