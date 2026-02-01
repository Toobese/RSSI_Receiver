package com.example.rssi_receiver.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rssi_receiver.core.model.Grid
import java.util.UUID

@Composable
fun GridSelectionScreenContent(
    mode: GridMode,
    grids: List<Grid>,
    onGridSelected: (UUID, GridMode) -> Unit,
    onCreateGrid: (name: String, width: Int, height: Int) -> Unit,
) {
    var showCreateDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Select Grid",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(grids) { grid ->
                GridRow(
                    grid = grid,
                    onClick = { onGridSelected(grid.id, mode) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showCreateDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("New Grid")
        }
    }

    if (showCreateDialog) {
        CreateGridDialog(
            onDismiss = { showCreateDialog = false },
            onCreate = { name, width, height ->
                onCreateGrid(name, width, height)
                showCreateDialog = false
            }
        )
    }
}
