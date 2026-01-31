package com.example.rssi_receiver.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost

import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.rssi_receiver.ui.screen.MapScreen
import com.example.rssi_receiver.ui.screen.MapSelectionScreen
import com.example.rssi_receiver.ui.screen.ModeSelectionScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val onBack: () -> Unit = {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
    }

    NavHost(
        navController = navController,
        startDestination = ModeRoute,
        modifier = modifier
    ) {
        composable<ModeRoute> {
            ModeSelectionScreen(
                onSelectMode = { mode ->
                    navController.navigate(MapSelectionRoute(mode))
                }
            )
        }

        composable<MapSelectionRoute> { backStackEntry ->
            val route: MapSelectionRoute = backStackEntry.toRoute()

            MapSelectionScreen(
                mode = route.mode,
//                onMapSelect = {
//                    navController.navigate(MapRoute) {
//                        popUpTo(ModeRoute) { inclusive = false }
//                    }
//                }
            )
        }

        composable<MapRoute> { MapScreen() }
    }
}
