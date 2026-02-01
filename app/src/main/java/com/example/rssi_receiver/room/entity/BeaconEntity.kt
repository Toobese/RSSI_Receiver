package com.example.rssi_receiver.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.rssi_receiver.core.model.Beacon
import java.util.UUID

@Entity(
    tableName = "beacon",
    foreignKeys = [
        ForeignKey(
            entity = MapEntity::class,
            parentColumns = ["id"],
            childColumns = ["mapId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("mapId")]
)
data class BeaconEntity(
    @PrimaryKey val id: UUID,
    val mapId: UUID,
    val xCoordinate: Float,
    val yCoordinate: Float
)

fun Beacon.toEntity() =
    BeaconEntity(
        id = id,
        mapId = mapId,
        xCoordinate = xCoordinate,
        yCoordinate = yCoordinate,
    )

fun BeaconEntity.toExternal() =
    Beacon(
        id = id,
        mapId = mapId,
        xCoordinate = xCoordinate,
        yCoordinate = yCoordinate,
    )
