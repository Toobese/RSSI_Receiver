package com.example.rssi_receiver.ui.screen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.rssi_receiver.core.model.FingerPrint

@Composable
fun GridCanvas(
    gridWidth: Int,
    gridHeight: Int,
    tileSize: Dp,
    panOffset: Offset,
    fingerPrints: List<FingerPrint>,
    onTileClick: (Int, Int) -> Unit,
) {
    val tileSizePx = with(LocalDensity.current) { tileSize.toPx() }

    val gridWidthPx = gridWidth * tileSizePx
    val gridHeightPx = gridHeight * tileSizePx

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(panOffset) {
                detectTapGestures { tapOffset ->

                    val adjustedX = tapOffset.x - panOffset.x
                    val adjustedY = tapOffset.y - panOffset.y

                    val gridX = (adjustedX / tileSizePx).toInt()
                    val gridY = (adjustedY / tileSizePx).toInt()

                    if (
                        gridX in 0 until gridWidth &&
                        gridY in 0 until gridHeight
                    ) {
                        onTileClick(gridX, gridY)
                    }
                }
            }
    ) {
        Canvas(
            modifier = Modifier.size(
                width = gridWidth * tileSize,
                height = gridHeight * tileSize
            )
        ) {
            val strokeWidth = 1.dp.toPx()
            val gridColor = Color(0xFFB0B0B0)

            withTransform({
                translate(panOffset.x, panOffset.y)
            }) {
                for (x in 0..gridWidth) {
                    val xPos = x * tileSizePx
                    drawLine(
                        color = gridColor,
                        start = Offset(xPos, 0f),
                        end = Offset(xPos, gridHeightPx),
                        strokeWidth = strokeWidth
                    )
                }
                for (y in 0..gridHeight) {
                    val yPos = y * tileSizePx
                    drawLine(
                        color = gridColor,
                        start = Offset(0f, yPos),
                        end = Offset(gridWidthPx, yPos),
                        strokeWidth = strokeWidth
                    )
                }
                fingerPrints.forEach { fingerPrint ->
                    val centerX = (fingerPrint.xCoordinate + 0.5f) * tileSizePx
                    val centerY = (fingerPrint.yCoordinate + 0.5f) * tileSizePx

                    drawCircle(
                        color = Color(0xFF4CAF50),
                        radius = tileSizePx * 0.3f,
                        center = Offset(centerX, centerY)
                    )

                    drawContext.canvas.nativeCanvas.drawText(
                        "F",
                        centerX,
                        centerY + (tileSizePx * 0.12f),
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.WHITE
                            textSize = tileSizePx * 0.4f
                            textAlign = android.graphics.Paint.Align.CENTER
                            isAntiAlias = true
                            typeface = android.graphics.Typeface.DEFAULT_BOLD
                        }
                    )
                }
            }
        }
    }
}

