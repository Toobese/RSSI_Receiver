package com.example.rssi_receiver.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.rssi_receiver.core.model.FingerPrint
import java.util.UUID

@Entity(
    tableName = "finger_print",
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
data class FingerPrintEntity(
    @PrimaryKey val id: UUID,
    val mapId: UUID,
    val friendlyId: Int,
    val xCoordinate: Float,
    val yCoordinate: Float
)

fun FingerPrint.toEntity() =
    FingerPrintEntity(
        id = id,
        mapId = mapId,
        friendlyId = friendlyId,
        xCoordinate = xCoordinate,
        yCoordinate = yCoordinate,
    )

fun FingerPrintEntity.toExternal() =
    FingerPrint(
        id = id,
        mapId = mapId,
        friendlyId = friendlyId,
        xCoordinate = xCoordinate,
        yCoordinate = yCoordinate,
    )