package com.example.rssi_receiver.core.model

data class BleDevice(
    val mac: String,
    val name: String?,
    val rssi: Int,
)