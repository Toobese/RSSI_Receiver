package com.example.rssi_receiver.core.model

import java.util.UUID

data class Beacon(
    val id: UUID,
    val gridId: UUID,
    val linkId: String,
    val xCoordinate: Float,
    val yCoordinate: Float,
)
