package com.example.rssi_receiver.core.model

import java.util.UUID

data class Map(
    val id: UUID,
    val name: String,
    val width: Int,
    val height: Int,
    val beacons: List<Beacon>,
    val fingerprints: List<FingerPrint>,
)
