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
    val name: String,
    val width: Int,
    val height: Int,
    )

fun Map.toEntity() =
    MapEntity(
        id = id,
        name = name,
        width = width,
        height = height,
    )

fun MapEntity.toExternal(beacons: List<Beacon>, fingerPrints: List<FingerPrint>): Map =
    Map(
        id = id,
        name = name,
        width = width,
        height = height,
        beacons = beacons,
        fingerprints = fingerPrints,
    )