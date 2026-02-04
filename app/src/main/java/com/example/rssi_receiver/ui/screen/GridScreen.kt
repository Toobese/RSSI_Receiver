package com.example.rssi_receiver.ui.screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rssi_receiver.viewmodel.GridViewModel
import com.example.rssi_receiver.viewmodel.GridViewState
import com.example.rssi_receiver.viewmodel.ModeViewState

@Composable
fun GridScreen(
    onBack: () -> Unit,
    gridViewModel: GridViewModel = hiltViewModel()
) {
    val viewState = gridViewModel.viewState.collectAsStateWithLifecycle().value
    when (viewState) {
        is GridViewState.Success -> {
            EditGridScreenContent(
                state = viewState,
                onBack = onBack,
                onChangeMode = { mode ->
                    gridViewModel.updateMode(mode)
                },
                onTileClick = { x, y ->
                    gridViewModel.onTileClick(x, y)
                },
                onDeviceSelect = { device ->
                    gridViewModel.onDeviceSelected(device)
                },
                onDismiss = { gridViewModel.hideBeaconDialog() }
            )
        }

        GridViewState.Loading -> {
            Log.d("banaan", "we loading")
        }
        GridViewState.Error -> {
            Log.d("banaan", "we errored")
        }
    }
}