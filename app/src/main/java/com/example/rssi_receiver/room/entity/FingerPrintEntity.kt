package com.example.rssi_receiver.room.entity

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
            entity = GridEntity::class,
            parentColumns = ["id"],
            childColumns = ["gridId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("gridId")]
)
data class FingerPrintEntity(
    @PrimaryKey val id: UUID,
    val gridId: UUID,
    val friendlyId: Int,
    val xCoordinate: Float,
    val yCoordinate: Float
)

fun FingerPrint.toEntity() =
    FingerPrintEntity(
        id = id,
        gridId = gridId,
        friendlyId = friendlyId,
        xCoordinate = xCoordinate,
        yCoordinate = yCoordinate,
    )

fun FingerPrintEntity.toExternal() =
    FingerPrint(
        id = id,
        gridId = gridId,
        friendlyId = friendlyId,
        xCoordinate = xCoordinate,
        yCoordinate = yCoordinate,
    )