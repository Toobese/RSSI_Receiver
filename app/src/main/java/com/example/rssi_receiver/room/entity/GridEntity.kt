package com.example.rssi_receiver.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rssi_receiver.core.model.Beacon
import com.example.rssi_receiver.core.model.FingerPrint
import com.example.rssi_receiver.core.model.Grid
import java.util.UUID

@Entity("grid")
data class GridEntity(
    @PrimaryKey val id: UUID,
    val name: String,
    val width: Int,
    val height: Int,
    )

fun Grid.toEntity() =
    GridEntity(
        id = id,
        name = name,
        width = width,
        height = height,
    )

fun GridEntity.toExternal(): Grid =
    Grid(
        id = id,
        name = name,
        width = width,
        height = height,
    )