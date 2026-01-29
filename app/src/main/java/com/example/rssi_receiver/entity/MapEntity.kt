package com.example.rssi_receiver.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rssi_receiver.core.model.Beacon
import com.example.rssi_receiver.core.model.FingerPrint
import com.example.rssi_receiver.core.model.Map
import com.example.rssi_receiver.core.model.Tile
import java.util.UUID

@Entity("map")
data class MapEntity(
    @PrimaryKey val id: UUID,
    val width: Int,
    val height: Int,
    val tiles: List<Tile>,
    val beacons: List<Beacon>,
    val fingerprints: List<FingerPrint>,
    )

fun Map.toEntity() =
    MapEntity(
        id = id,
        width = width,
        height = height,
        tiles = tiles,
        beacons = beacons,
        fingerprints = fingerprints
    )