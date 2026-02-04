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
import android.bluetooth.le.ScanSettings
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import com.example.rssi_receiver.ble.BleRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TAG = "ScanService"

@AndroidEntryPoint
class BleScanService: Service() {
    private lateinit var scanner: BluetoothLeScanner
    @Inject
    lateinit var bleRepository: BleRepository

    private val callback = object : ScanCallback() {
        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            result ?: return

            val record = result.scanRecord

            if (result.rssi >= -85) {
                Log.d("BLE_RAW", buildString {
                    append("mac=${result.device.address} ")
                    append("rssi=${result.rssi} ")
                    append("name=${result.device.name} ")

//                record?.manufacturerSpecificData?.let { msd ->
//                    for (i in 0 until msd.size()) {
//                        val key = msd.keyAt(i)
//                        append("mfg[$key]=${msd[key]?.toHex()} ")
//                    }
//                }

                    record?.serviceUuids?.let {
                        append("uuids=$it")
                    }
                })
            }

            bleRepository.onScan(
                mac = result.device.address,
                rssi = result.rssi,
                name = result.device.name
            )
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
        val settings = ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build()
        scanner = manager.adapter.bluetoothLeScanner
        scanner.startScan(null, settings, callback)
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

fun ByteArray.toHex(): String =
    joinToString("") { "%02X".format(it) }
