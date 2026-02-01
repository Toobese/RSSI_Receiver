package com.example.rssi_receiver.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.rssi_receiver.ui.screen.GridScreen
import com.example.rssi_receiver.ui.screen.GridSelectionScreen
import com.example.rssi_receiver.ui.screen.ModeSelectionScreen
import java.util.UUID

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
                    navController.navigate(GridSelectionRoute(mode))
                }
            )
        }

        composable<GridSelectionRoute> { backStackEntry ->
            val route: GridSelectionRoute = backStackEntry.toRoute()
            GridSelectionScreen(
                mode = route.mode,
                onGridSelected = { gridId, mode ->
                    navController.navigate(GridRoute(gridId = gridId.toString(), mode = mode))
                },
                onBack = onBack
            )
        }

        composable<GridRoute> { backStackEntry ->
            val route: GridRoute = backStackEntry.toRoute()
            GridScreen(gridId = UUID.fromString(route.gridId), mode = route.mode)
        }
    }
}
