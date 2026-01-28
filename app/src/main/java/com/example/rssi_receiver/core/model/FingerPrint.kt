package com.example.rssi_receiver.core.model

import java.util.UUID

data class FingerPrint(
    val id: UUID,
    val friendlyId: Int,
    val xCoordinate: Float,
    val yCoordinate: Float,
)
