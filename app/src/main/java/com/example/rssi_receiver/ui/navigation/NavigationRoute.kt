package com.example.rssi_receiver.ui.navigation

import com.example.rssi_receiver.ui.screen.GridMode
import kotlinx.serialization.Serializable


interface NavigationRoute

@Serializable
object ModeRoute : NavigationRoute

@Serializable
object GridRoute : NavigationRoute

@Serializable
data class GridSelectionRoute(val mode: GridMode) : NavigationRoute
