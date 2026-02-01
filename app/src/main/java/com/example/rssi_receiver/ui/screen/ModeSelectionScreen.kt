package com.example.rssi_receiver.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rssi_receiver.ui.theme.RSSI_receiverTheme
import kotlinx.serialization.Serializable

@Composable
fun ModeSelectionScreen(
    onSelectMode: (GridMode) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Select mode",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        ModeButton(
            text = "View mode",
            onClick = { onSelectMode(GridMode.VIEW_MODE) }
        )

        ModeButton(
            text = "Edit mode",
            onClick = { onSelectMode(GridMode.EDIT_MODE) }
        )
    }
}

@Composable
private fun ModeButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge
        )
    }
}


@Serializable
enum class GridMode() {
    EDIT_MODE,
    VIEW_MODE
}

@Preview
@Composable
private fun ModeSelectionScreenPreview(modifier: Modifier = Modifier) {
    RSSI_receiverTheme {
        ModeSelectionScreen({})
    }
}