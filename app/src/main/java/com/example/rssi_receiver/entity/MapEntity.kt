package com.example.rssi_receiver.entity

import java.util.UUID

@Entity("map")
data class Map(
    @PrimaryKey val id: UUID,

) {
}