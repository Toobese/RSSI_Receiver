package com.example.rssi_receiver.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.rssi_receiver.core.model.BleDevice

@Composable
fun CreateBeaconDialog(
    devices: List<BleDevice>,
    onSelect: (BleDevice) -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select nearby beacon") },
        text = {
            Column {
                devices.forEach { device ->
                    BeaconCandidateRow(device, onSelect)
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun BeaconCandidateRow(
    device: BleDevice,
    onSelect: (BleDevice) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(device) }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(device.name ?: "Unknown device")
            Text(
                text = device.mac,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Text(
            text = "${device.rssi} dBm",
            fontWeight = FontWeight.Bold
        )
    }
}

