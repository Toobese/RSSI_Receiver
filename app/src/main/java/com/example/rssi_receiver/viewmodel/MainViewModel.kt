package com.example.rssi_receiver.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.rssi_receiver.ble.BleRepository

const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
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
}