package com.example.rssi_receiver.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.rssi_receiver.core.model.Product
import java.util.UUID

@Entity(
    tableName = "product",
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
data class ProductEntity(
    @PrimaryKey val id: UUID,
    val mapId: UUID,
    val name: String,
    val price: Float,
    val xCoordinate: Int,
    val yCoordinate: Int,
)

fun Product.toEntity() =
    ProductEntity(
        id = id,
        mapId = mapId,
        name = name,
        price = price,
        xCoordinate = xCoordinate,
        yCoordinate = yCoordinate,
    )

fun ProductEntity.toExternal() =
    Product(
        id = id,
        mapId = mapId,
        name = name,
        price = price,
        xCoordinate = xCoordinate,
        yCoordinate = yCoordinate,
    )