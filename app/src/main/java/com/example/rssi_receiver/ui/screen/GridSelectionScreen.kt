package com.example.rssi_receiver.ui.screen

import android.util.Log
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
    onBack: () -> Unit,
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
                },
                onBack = onBack,
            )
        }

        ModeViewState.Loading -> {
            Log.d("banaan", "we loading")
        }
        ModeViewState.Error -> {
            Log.d("banaan", "we errored")
        }
    }

}