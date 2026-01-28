package com.example.rssi_receiver.ble

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleRepository {
    private val _rssis = MutableStateFlow<Map<String, Int>>(emptyMap())
    val rssis: StateFlow<Map<String, Int>> = _rssis

    fun updateRssi(mac: String, rssi: Int) {
        _rssis.value += (mac to rssi)
    }

    companion object { val instance = BleRepository() }
}