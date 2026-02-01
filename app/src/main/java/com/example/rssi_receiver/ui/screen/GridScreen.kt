package com.example.rssi_receiver.ui.screen

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rssi_receiver.viewmodel.GridViewModel
import com.example.rssi_receiver.viewmodel.GridViewState
import com.example.rssi_receiver.viewmodel.ModeViewState

@Composable
fun GridScreen(
    mode: GridMode,
    onBack: () -> Unit,
    gridViewModel: GridViewModel = hiltViewModel()
) {
    val viewState = gridViewModel.viewState.collectAsStateWithLifecycle().value
    when (viewState) {
        is GridViewState.Success -> {
            EditGridScreenContent(
                grid = viewState.grid,
                beacons = viewState.beacons,
                products = viewState.products,
                fingerPrints = viewState.fingerPrints,
                onBack = onBack,
            )
        }

        GridViewState.Loading -> { /* loading UI */ }
        GridViewState.Error -> { /* error UI */ }
    }
}