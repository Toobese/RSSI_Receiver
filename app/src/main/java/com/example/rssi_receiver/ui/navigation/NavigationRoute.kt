package com.example.rssi_receiver.ui.navigation

import com.example.rssi_receiver.ui.screen.GridMode
import kotlinx.serialization.Serializable
import java.util.UUID


interface NavigationRoute

@Serializable
object ModeRoute : NavigationRoute

@Serializable
data class GridRoute(
    val mode: GridMode,
    val gridId: String,
) : NavigationRoute

@Serializable
data class GridSelectionRoute(val mode: GridMode) : NavigationRoute
