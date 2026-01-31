package com.example.rssi_receiver

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.rssi_receiver.service.BleScanService
import com.example.rssi_receiver.ui.navigation.AppNavHost
import com.example.rssi_receiver.ui.screen.MapScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    private val blePermissions =
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
        )

    private val permissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val granted = permissions.values.all { it }
            Log.d("BLE", "Permission result: $permissions")

            if (granted) {
                Log.d("BLE", "Permissions granted, starting service")
                startBleService()
            } else {
                Log.e("BLE", "BLE permissions denied")
            }
        }

    private fun startBleService() {
        ContextCompat.startForegroundService(
            this,
            Intent(this, BleScanService::class.java)
        )
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val hasPermissions = blePermissions.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }
        if (hasPermissions) {
            Log.d("BLE", "BLE permissions already granted")
            startBleService()
        } else {
            Log.d("BLE", "Requesting BLE permissions")
            permissionLauncher.launch(blePermissions)
        }
        setContent {
            val navController = rememberNavController()
            AppNavHost(
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
