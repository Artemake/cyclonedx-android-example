package com.learninglakner.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.learninglakner.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                upper()
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun upper() {

        var visible by remember {
            mutableStateOf(true)
        }
        val density = LocalDensity.current

        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Red)
                    .clickable { visible = false })


            val b1 = @Composable { triangle() }

            val b2 = @Composable {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Black)
                        .clickable { }
                )
            }

            Layout(
                contents = listOf(b1, b2),
                modifier = Modifier.padding(8.dp)
            ) { (b1Measurable, b2Measurable), constraints ->

                // measure
                val b1Placeable = b1Measurable[0].measure(constraints)

                val b2Placeable = b2Measurable[0].measure(constraints)

                // placement
                layout(
                    width = b1Placeable.width,
                    height = b1Placeable.height + b2Placeable.height
                ) {
                    b1Placeable.place(542, 45)
                    b2Placeable.place(4.dp.toPx().toInt(), 45 + b1Placeable.height)
                }
            }
        }


    }

    @Composable
    fun triangle() {

        Canvas(modifier = Modifier
            .size(50.dp)
            .clickable { }, onDraw = {
            val size = 50.dp.toPx()
            val trianglePath = Path().apply {
                // Moves to top center position
                moveTo(size / 2f, size)
                // Add line to bottom right corner
                lineTo(size, 0f)
                // Add line to bottom left corner
                lineTo(0f, 0f)
            }

            drawPath(
                color = Color.Green,
                path = trianglePath
            )
        })
    }
}

@Preview
@Composable
private fun AnimatedVisibilityWithEnterAndExit() {
    // [START android_compose_animations_animated_visibility_enter_exit]
    var visible by remember { mutableStateOf(true) }
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically {
            // Slide in from 40 dp from the top.
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        Text("Hello", Modifier.fillMaxWidth().height(200.dp).clickable { visible = !visible })
    }
    // [END android_compose_animations_animated_visibility_enter_exit]
}