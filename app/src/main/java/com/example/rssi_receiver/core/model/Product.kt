package com.example.rssi_receiver.core.model

import java.util.UUID

data class Product(
    val id: UUID,
    val name: String,
    val price: Float,
)
