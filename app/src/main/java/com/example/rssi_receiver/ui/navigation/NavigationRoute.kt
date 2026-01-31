package com.example.rssi_receiver.ui.navigation

import com.example.rssi_receiver.ui.screen.MapMode
import kotlinx.serialization.Serializable


interface NavigationRoute

@Serializable
object ModeRoute : NavigationRoute

@Serializable
object MapRoute : NavigationRoute

@Serializable
data class MapSelectionRoute(val mode: MapMode) : NavigationRoute
