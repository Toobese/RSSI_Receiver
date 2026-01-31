package com.example.rssi_receiver.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.rssi_receiver.viewmodel.ModeViewModel

@Composable
fun RssiScreen(
    modeViewModel: ModeViewModel =
        hiltViewModel<ModeViewModel, ModeViewModel.ModeViewModelFactory> {
            it.create()
        },
) {
    val rssis by modeViewModel.rssis.collectAsState()

    LazyColumn {
        items(rssis.entries.toList()) { (mac, rssi) ->
            Text(text = "$mac → $rssi dBm")
        }
    }
}
