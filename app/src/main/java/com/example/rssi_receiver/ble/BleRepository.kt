package com.example.rssi_receiver.ble

import android.util.Log
import com.example.rssi_receiver.core.model.BleDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleRepository @Inject constructor() {
    private val _scans = MutableStateFlow<Map<String, BleDevice>>(emptyMap())

    val scans: StateFlow<List<BleDevice>> =
        _scans.map { it.values.toList() }
            .stateIn(
                CoroutineScope(Dispatchers.Default),
                SharingStarted.Eagerly,
                emptyList()
            )
    fun onScan(mac: String, rssi: Int, name: String?) {
        _scans.update { current ->
            current + (mac to BleDevice(mac, name, rssi))
        }
    }
}