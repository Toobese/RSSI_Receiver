package com.example.rssi_receiver.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rssi_receiver.core.model.Grid
import com.example.rssi_receiver.repository.GridRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

const val TAG = "MainViewModel"

@HiltViewModel
class ModeViewModel @Inject constructor(
    private val gridRepository: GridRepository,
) : ViewModel() {
//    private val bleRepository: BleRepository = BleRepository.instance
//    val rssis: StateFlow<Map<String, Int>> = bleRepository.rssis

    val viewState: MutableStateFlow<ModeViewState> = MutableStateFlow(ModeViewState.Loading)

    init {
        Log.d(TAG, "initializing viewmodel")
        viewModelScope.launch {
            viewState.update {
                val grids = gridRepository.getAllGrids()
                it.success()?: ModeViewState.Success(grids = grids)
            }

//            rssis.collect { rssiMap ->
//                Log.d(TAG, "new rssi")
//            }
        }
    }

    fun createGrid(name: String, width: Int, height: Int) {
        viewModelScope.launch {
            val grid = Grid(
                id = UUID.randomUUID(),
                name = name,
                width = width,
                height = height,
                fingerprints = emptyList(),
                beacons = emptyList()
            )
            gridRepository.insertGrid(grid)
        }
    }
}

sealed interface ModeViewState {
    data class Success(
        val grids: List<Grid>,
    ) : ModeViewState

    data object Error : ModeViewState

    data object Loading : ModeViewState

    fun success(): Success? = this as? Success
}