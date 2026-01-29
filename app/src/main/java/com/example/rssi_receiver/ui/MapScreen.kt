package com.example.rssi_receiver.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MapScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(text = "10x10 Grid")
        Spacer(modifier = Modifier.size(10.dp))

        Box(Modifier
            .fillMaxSize()
            .background(Color.Green)
            .drawWithContent {
                val number = 10
                val tileSize = Size(
                    width = size.width / number,
                    height = size.height / number,
                )
                var x = 0
                var y = 0
                do {
                    val dstRect = Rect(
                        left = x * tileSize.width,
                        top = y * tileSize.height,
                        right = (x + 1) * tileSize.width,
                        bottom = (y + 1) * tileSize.height,
                    )
                    val xIsEven = (x % 2 == 0)
                    val yIsEven = (y % 2 == 0)
                    val color = if (yIsEven && !xIsEven) Color.Red else Color.Green
                    drawRect(color = color, topLeft = dstRect.topLeft, size = dstRect.size)

                    if (x < number - 1) {
                        x++
                    } else if (x == number - 1) {
                        x = 0
                        y++
                    }
                } while (y < number)
            }
        )
    }
}