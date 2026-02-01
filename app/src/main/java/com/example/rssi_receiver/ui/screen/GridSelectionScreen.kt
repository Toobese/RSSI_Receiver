package com.example.rssi_receiver.ui.screen

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rssi_receiver.viewmodel.ModeViewModel
import com.example.rssi_receiver.viewmodel.ModeViewState
import java.util.UUID

@Composable
fun GridSelectionScreen(
    mode: GridMode,
    onGridSelected: (gridId: UUID, mode: GridMode) -> Unit,
    modeViewModel: ModeViewModel = hiltViewModel()
    ) {
    val viewState = modeViewModel.viewState.collectAsStateWithLifecycle().value
    when (viewState) {
        is ModeViewState.Success -> {
            GridSelectionScreenContent(
                mode = mode,
                grids = viewState.grids,
                onGridSelected = onGridSelected,
                onCreateGrid = { name, width, height ->
                    modeViewModel.createGrid(name, width, height)
                }
            )
        }

        ModeViewState.Loading -> { /* loading UI */ }
        ModeViewState.Error -> { /* error UI */ }
    }

}