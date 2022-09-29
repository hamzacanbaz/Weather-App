package com.hamzacanbaz.weatherapp.presentation.addLocation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.hamzacanbaz.weatherapp.R
import com.hamzacanbaz.weatherapp.ui.theme.GradientDark
import com.hamzacanbaz.weatherapp.ui.theme.GradientLight
import com.hamzacanbaz.weatherapp.ui.theme.Purple700
import com.hamzacanbaz.weatherapp.util.enums.SearchWidgetState

@Composable
fun AddLocationScreen(
    addLocationScreenViewModel: AddLocationScreenViewModel = hiltViewModel(),
    onLocationSelected: () -> Unit
) {

    val searchWidgetState by addLocationScreenViewModel.searchWidgetState
    val searchTextState by addLocationScreenViewModel.searchTextState
    val countries by addLocationScreenViewModel.countries

    val openDialog = remember { mutableStateOf(false) }
    var clickedLocation = remember { mutableStateOf("") }
//    var text by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            MainAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    addLocationScreenViewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    addLocationScreenViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    Log.d("Searched Text", it)
                },
                onSearchTriggered = {
                    addLocationScreenViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )
        }
    ) {
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
                LazyColumn {
                    items(countries) {

                        if (openDialog.value) {
                            CustomDialog(clickedLocation.value,openDialog, onAcceptDialog = {
//                                println(clickedLocation.value)
                                addLocationScreenViewModel.addLocationToLocalDb(
                                    clickedLocation.value,
                                    onSuccess = {
                                        onLocationSelected.invoke()
                                    })

                                openDialog.value = false


                            },
                                onCancelDialog = {

                                })
                        }
                        LocationItem(it) {
                            println(it)
                            clickedLocation.value = it
                            openDialog.value = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LocationItem(text: String, onClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(1.dp, GradientLight, RoundedCornerShape(8.dp))
            .clickable {
                onClicked()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, modifier = Modifier.padding(0.dp, 8.dp))
    }
}

@Preview
@Composable
fun LocationItemP(text: String = "asd") {
    LocationItem(text = "asdasdasda", {})
}


@Composable
fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Home"
            )
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }
}

@Composable
fun CustomDialog(
    locationName:String,
    openDialogCustom: MutableState<Boolean>,
    onAcceptDialog: () -> Unit,
    onCancelDialog: () -> Unit
) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
        CustomDialogUI(openDialogCustom = openDialogCustom, onAccept = {
            onAcceptDialog.invoke()
        }, onCancel = {
            openDialogCustom.value = false
            onCancelDialog.invoke()
        }, locationName = locationName)
    }
}

//Layout
@Composable
fun CustomDialogUI(
    locationName: String,
    modifier: Modifier = Modifier,
    openDialogCustom: MutableState<Boolean>,
    onAccept: () -> Unit,
    onCancel: () -> Unit
) {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp)
    ) {
        Column(
            modifier
                .background(Color.White)
        ) {
            Image(
                painterResource(R.drawable.location),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth()
            )


            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = locationName,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Do you want to add this location to your home screen?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth()
                )
            }
            //.......................................................................
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(Purple700),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(onClick = {
                    openDialogCustom.value = false
                }) {

                    Text(
                        "Cancel",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .clickable {
                                onCancel.invoke()
                            }
                    )
                }
                TextButton(onClick = {
                    openDialogCustom.value = false
                }) {
                    Text(
                        "Accept",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .clickable {
                                onAccept.invoke()
                            }
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(onSearchClicked = {})
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Some random text",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}
