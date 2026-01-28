package com.example.rssi_receiver.service

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import com.example.rssi_receiver.ble.BleRepository

const val TAG = "ScanService"

class BleScanService: Service() {
    private lateinit var scanner: BluetoothLeScanner
    private val bleRepository = BleRepository.instance

    private val callback = object : ScanCallback() {
        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            Log.d(
                TAG,
                "APP RESULT: ${result?.device?.address} rssi=${result?.rssi}"
            )
            result?.let {
                Log.d(TAG, "we received a scan result: ${result.rssi} from: ${result.device.name}")
                bleRepository.updateRssi(mac = result.device.address, rssi = result.rssi)
            }
        }
    }

    @SuppressLint("ForegroundServiceType")
    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    override fun onCreate() {
        super.onCreate()
        val notification = NotificationCompat.Builder(this, "ble_channel")
            .setContentTitle("BLE scanning active")
            .setContentText("Scanning for nearby beacons")
            .setSmallIcon(R.drawable.stat_sys_data_bluetooth)
            .build()

        createChannel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                1,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE
            )
        } else {
            startForeground(1, notification)
        }

        val manager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        scanner = manager.adapter.bluetoothLeScanner
        scanner.startScan(callback)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    override fun onDestroy() {
        scanner.stopScan(callback)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "ble_channel",
                "BLE Scanning",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }

}