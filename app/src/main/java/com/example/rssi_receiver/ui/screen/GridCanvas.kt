package com.example.rssi_receiver.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun GridCanvas(
    gridWidth: Int,
    gridHeight: Int,
    tileSize: Dp,
    panOffset: Offset,
) {
    val tileSizePx = with(LocalDensity.current) { tileSize.toPx() }

    val gridWidthPx = gridWidth * tileSizePx
    val gridHeightPx = gridHeight * tileSizePx

    Canvas(
        modifier = Modifier.size(
            width = gridWidth * tileSize,
            height = gridHeight * tileSize
        )
    ) {
        val strokeWidth = 1.dp.toPx()
        val gridColor = Color(0xFFB0B0B0)

        // Apply pan transform
        withTransform({
            translate(panOffset.x, panOffset.y)
        }) {
            // Vertical lines
            for (x in 0..gridWidth) {
                val xPos = x * tileSizePx
                drawLine(
                    color = gridColor,
                    start = Offset(xPos, 0f),
                    end = Offset(xPos, gridHeightPx),
                    strokeWidth = strokeWidth
                )
            }

            // Horizontal lines
            for (y in 0..gridHeight) {
                val yPos = y * tileSizePx
                drawLine(
                    color = gridColor,
                    start = Offset(0f, yPos),
                    end = Offset(gridWidthPx, yPos),
                    strokeWidth = strokeWidth
                )
            }
        }
    }
}
