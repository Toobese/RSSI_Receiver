package com.example.rssi_receiver.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.rssi_receiver.ble.BleRepository
import com.example.rssi_receiver.repository.GridRepository
import dagger.assisted.AssistedFactory
import com.example.rssi_receiver.core.model.Grid

const val TAG = "MainViewModel"

class ModeViewModel @AssistedInject constructor(
    private val gridRepository: GridRepository,
) : ViewModel() {
    private val bleRepository: BleRepository = BleRepository.instance
    val rssis: StateFlow<Map<String, Int>> = bleRepository.rssis

    init {
        Log.d(TAG, "initializing viewmodel")
        viewModelScope.launch {
            rssis.collect { rssiMap ->
                Log.d(TAG, "new rssi")
            }
        }
    }

    @AssistedFactory
    interface ModeViewModelFactory {
        fun create(): ModeViewModel
    }
}

sealed interface ModeViewState {
    data class Success(
        val grids: List<Grid>,
    )
}