package com.example.rssi_receiver.core.model

import java.util.UUID

data class Beacon(
    val id: UUID,
    val xCoordinate: Float,
    val yCoordinate: Float,
)