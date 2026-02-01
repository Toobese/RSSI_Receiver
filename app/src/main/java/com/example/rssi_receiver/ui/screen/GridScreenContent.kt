package com.example.rssi_receiver.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.rssi_receiver.R
import com.example.rssi_receiver.core.model.Beacon
import com.example.rssi_receiver.core.model.FingerPrint
import com.example.rssi_receiver.core.model.Grid
import com.example.rssi_receiver.core.model.Product
import com.example.rssi_receiver.viewmodel.GridViewModel

private val TILE_SIZE = 40.dp

@Composable
fun EditGridScreenContent(
    grid: Grid,
    beacons: List<Beacon>,
    products: List<Product>,
    fingerPrints: List<FingerPrint>,
    onBack: () -> Unit,
) {
    var panOffset by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onBack,
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                )
            }

            Text(
                text = grid.name,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        panOffset += dragAmount
                    }
                }
        ) {
            GridCanvas(
                gridWidth = grid.width,
                gridHeight = grid.height,
                tileSize = TILE_SIZE,
                panOffset = panOffset
            )
        }
    }
}