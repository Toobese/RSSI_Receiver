package com.example.rssi_receiver.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "product",
    foreignKeys = [
        ForeignKey(
            entity = MapEntity::class,
            parentColumns = ["id"],
            childColumns = ["routeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("mapId")]
    )
data class ProductEntity(
    @PrimaryKey val id: UUID,
    val routeId: UUID,
    val name: String,
    val price: Float,
    val xCoordinate: Int,
    val yCoordinate: Int,
)