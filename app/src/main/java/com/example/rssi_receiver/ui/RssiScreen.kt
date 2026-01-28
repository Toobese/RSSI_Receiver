package com.example.rssi_receiver.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.rssi_receiver.viewmodel.MainViewModel

@Composable
fun RssiScreen(viewModel: MainViewModel) {
    val rssis by viewModel.rssis.collectAsState()

    LazyColumn {
        items(rssis.entries.toList()) { (mac, rssi) ->
            Text(text = "$mac → $rssi dBm")
        }
    }
}
