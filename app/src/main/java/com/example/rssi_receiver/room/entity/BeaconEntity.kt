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
            entity = GridEntity::class,
            parentColumns = ["id"],
            childColumns = ["gridId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("gridId")]
)
data class BeaconEntity(
    @PrimaryKey val id: UUID,
    val gridId: UUID,
    val linkId: String,
    val xCoordinate: Float,
    val yCoordinate: Float
)

fun Beacon.toEntity() =
    BeaconEntity(
        id = id,
        gridId = gridId,
        linkId = linkId,
        xCoordinate = xCoordinate,
        yCoordinate = yCoordinate,
    )

fun BeaconEntity.toExternal() =
    Beacon(
        id = id,
        gridId = gridId,
        linkId = linkId,
        xCoordinate = xCoordinate,
        yCoordinate = yCoordinate,
    )
