package com.example.rssi_receiver.core.model

import java.util.UUID

data class Tile(
    val id: UUID,
    val xCoordinate: Int,
    val yCoordinate: Int,
    val products: List<Product>?,
    val beacon: Beacon?,
    val fingerPrint: FingerPrint?,
)