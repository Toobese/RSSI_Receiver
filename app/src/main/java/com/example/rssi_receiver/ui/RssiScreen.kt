package com.example.rssi_receiver.ui

import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Text
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
