package com.example.rssi_receiver.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rssi_receiver.core.model.Grid

@Composable
fun GridRow(
    grid: Grid,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = grid.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Size: ${grid.width} × ${grid.height}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
