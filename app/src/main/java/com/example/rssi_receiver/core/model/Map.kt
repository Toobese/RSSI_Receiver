package com.example.rssi_receiver.core.model

data class Map(
    val width: Int,
    val height: Int,
    val tiles: List<Tile>,
    val beacons: List<Beacon>,
    val fingerprints: List<FingerPrint>,
)
