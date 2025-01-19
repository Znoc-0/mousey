package com.example.trackpad

import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.concurrent.thread

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NotConstructor")
@Composable
fun Home() {
    var x by remember { mutableFloatStateOf(0f) }
    var y by remember { mutableFloatStateOf(0f) }
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels
    var lastX by remember { mutableFloatStateOf(0f) }
    var lastY by remember { mutableFloatStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.background(color = Color.Black),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    scrolledContainerColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .padding(12.dp),
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Filled.Person, contentDescription = null)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "TrackPad",
                        style = TextStyle(color = Color.White)
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(color = Color.Red)
                        .fillMaxWidth()
                        .height(500.dp)
//                        .pointerInput(Unit) {
//                            detectTapGestures { offset ->
//                                x = offset.x
//                                y = offset.y
//
//                                sendTouchData(data().webSocket,x, y, "touch")
//                            }
//                        }

                        .pointerInteropFilter { event ->
                            when (event.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    // Start dragging
                                    lastX = event.x
                                    lastY = event.y
                                    isDragging = true

                                    sendTouchData(data().webSocket, event.x, event.y, "down")
                                }

                                MotionEvent.ACTION_MOVE -> {
                                    if (isDragging) {
                                        // Send drag data, continuously update
                                        val deltaX = event.x - lastX
                                        val deltaY = event.y - lastY
                                        sendTouchData(data().webSocket, event.x, event.y, "drag")

                                        // Update last position to current
                                        lastX = event.x
                                        lastY = event.y
                                    }
                                }

                                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                                    // Stop dragging when the touch is released
                                    sendTouchData(data().webSocket, event.x, event.y, "up")
                                    isDragging = false
                                }
                            }
                            true
                        }
                ) {
                    Column {
                        Text(
                            text = "X: $x",
                            style = TextStyle(color = Color.White)
                        )
                        Text(
                            text = "Y: $y",
                            style = TextStyle(color = Color.White)
                        )
                        Text(
                            text = "Screen Width: $screenWidth",
                            style = TextStyle(color = Color.White),
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "Screen Height: $screenHeight",
                            style = TextStyle(color = Color.White),
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
                Row (
                    modifier = Modifier
                        .padding(16.dp)
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .height(100.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly

                ){

                    Row(

                        modifier = Modifier
                            .background(color = Color.Green)
                            .fillMaxHeight()
                            .width(200.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        Log.d("TrackPad", "Long Pressed")
                                        thread(start = true) {
                                            sendTouchData(data().webSocket, x, y, "long_press_left")
                                        }
                                        //sendTouchData(data().webSocket, x, y, "long_press_left")
                                    },
                                    onDoubleTap = {
                                        Log.d("TrackPad", "Double Tapped")
                                        sendTouchData(data().webSocket, x, y, "double_tap_left")
                                    },
                                    onTap = {
                                        Log.d("TrackPad", "Tapped")

//                                        sendTouchData(data().webSocket, x, y, "tap_left")
                                        thread (
                                            start = true
                                        ){
                                            sendTouchData(data().webSocket, x, y, "tap_left")
                                        }

                                    },
                                )
                            }



                    )
                    {
                        Text(
                            text = "left",
                            style = TextStyle(color = Color.White)
                        )
                    }
                                      Row(
                        modifier = Modifier
                            .background(color = Color.Blue)
                            .fillMaxHeight()
                            .width(200.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        Log.d("TrackPad", "Long Pressed")
                                        sendTouchData(data().webSocket, x, y, "long_press_right")
                                    },
                                    onDoubleTap = {
                                        Log.d("TrackPad", "Double Tapped")
                                        sendTouchData(data().webSocket, x, y, "double_tap_right")
                                    },
                                    onTap = {
                                        Log.d("TrackPad", "Tapped")

                                        sendTouchData(data().webSocket, x, y, "tap_right")
                                    },

                                )
                            }

                    )

                    {
                        Text(
                            text = "right",
                            style = TextStyle(color = Color.White)
                        )
                    }




                }

            }
        }
    )
}
