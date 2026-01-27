package rssi_receiver

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import rssi_receiver.service.BleScanService
import rssi_receiver.ui.RssiScreen
import rssi_receiver.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    private val permissions = arrayOf(
        android.Manifest.permission.BLUETOOTH_SCAN,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions(permissions, 0)
        enableEdgeToEdge()
        startService(Intent(this, BleScanService::class.java))
        setContent {
            val viewModel: MainViewModel = viewModel()
            RssiScreen(viewModel)
        }
    }
}