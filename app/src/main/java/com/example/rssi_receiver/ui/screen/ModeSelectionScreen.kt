package com.example.rssi_receiver.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable

@Composable
fun ModeSelectionScreen(onSelectMode: (MapMode) -> Unit) {
    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Button(
            onClick = { onSelectMode(MapMode.VIEW_MODE) },
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            Text("View mode")
        }

        Button(
            onClick = { onSelectMode(MapMode.EDIT_MODE) },
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            Text(text = "Edit mode")
        }
    }
}

@Serializable
enum class MapMode() {
    EDIT_MODE,
    VIEW_MODE
}