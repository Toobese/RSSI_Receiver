package com.example.rssi_receiver.ble

class RssiBuffer(private val alpha: Double = 0.2) {

    private var value: Double? = null

    fun update(rssi: Int): Double {
        value = value?.let {
            it * (1 - alpha) + rssi * alpha
        } ?: rssi.toDouble()

        return value!!
    }
}
