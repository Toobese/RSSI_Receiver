package com.example.rssi_receiver.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rssi_receiver.ble.BleRepository
import com.example.rssi_receiver.core.model.Beacon
import com.example.rssi_receiver.core.model.BleDevice
import com.example.rssi_receiver.core.model.FingerPrint
import com.example.rssi_receiver.core.model.Grid
import com.example.rssi_receiver.core.model.Product
import com.example.rssi_receiver.repository.BeaconRepository
import com.example.rssi_receiver.repository.FingerPrintRepository
import com.example.rssi_receiver.repository.GridRepository
import com.example.rssi_receiver.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

private const val TAG = "GridViewModel"

@HiltViewModel
class GridViewModel @Inject constructor(
    private val gridRepository: GridRepository,
    private val productRepository: ProductRepository,
    private val beaconRepository: BeaconRepository,
    private val fingerPrintRepository: FingerPrintRepository,
    private val bleRepository: BleRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val viewState: MutableStateFlow<GridViewState> = MutableStateFlow(GridViewState.Loading)
    private val gridId: UUID = UUID.fromString(requireNotNull(savedStateHandle["gridId"]))

    init {
        Log.d(TAG, "initializing viewmodel")
        viewModelScope.launch {
            viewState.update {
                when (val grid = gridRepository.getGridById(gridId)) {
                    null -> it
                    else -> {
                        val products = productRepository.getProductsByGridId(gridId)
                        val beacons = beaconRepository.getBeaconsByGridId(gridId)
                        val fingerPrints = fingerPrintRepository.getFingerPrintsByGridId(gridId)
                        it.success() ?: GridViewState.Success(
                            grid = grid,
                            beacons = beacons,
                            products = products,
                            fingerPrints = fingerPrints,
                            editMode = EditMode.VIEW_MODE,
                        )
                    }
                }
            }
        }
        viewModelScope.launch {
            bleRepository.scans.collect { scanned ->
                viewState.update { it ->
                    val state = it.success() ?: return@update it
                    state.copy(
                        scannedDevices = filterUnlinkedDevices(
                            devices = scanned.filter { it.rssi >= -85 },
                            beacons = state.beacons
                        )
                    )
                }
            }
        }
    }

    fun onTileClick(x: Float, y: Float) {
        val state = viewState.value.success() ?: return
        Log.d("banaan", "${state.editMode}")
        when (state.editMode) {
            EditMode.FINGER_PRINT_MODE -> handleFingerPrint(x, y, state)
            EditMode.PRODUCT_MODE -> handleProduct(x, y, state)
            EditMode.BEACON_MODE -> handleBeacon(x, y, state)
            EditMode.DELETE_MODE -> handleDelete(x, y, state)
            EditMode.VIEW_MODE -> return
        }
    }

    private fun handleFingerPrint(x: Float, y: Float, state: GridViewState.Success) {
        Log.d("banaan", "x: $x and y: $y")
        viewModelScope.launch {
            val fingerPrint = FingerPrint(UUID.randomUUID(), state.grid.id, x, y)
            fingerPrintRepository.insertFingerPrints(listOf(fingerPrint))
            viewState.update {
                (it as? GridViewState.Success)?.copy(fingerPrints = it.fingerPrints + fingerPrint)
                    ?: it
            }
        }
    }

    private fun handleBeacon(x: Float, y: Float, state: GridViewState.Success) {
        viewState.update {
            (it as? GridViewState.Success)?.copy(
                temporaryCoordinates = TemporaryCoordinates(x, y),
                showCreateBeaconDialog = true
            ) ?: it
        }
    }

    private fun handleDelete(x: Float, y: Float, state: GridViewState.Success) {
        Log.d("banaan", "x: $x and y: $y")
    }

    private fun handleProduct(x: Float, y: Float, state: GridViewState.Success) {
        Log.d("banaan", "x: $x and y: $y")
    }

    fun onDeviceSelected(device: BleDevice) {
        val state = viewState.value.success() ?: return
        val coords = state.temporaryCoordinates ?: return

        val beacon = Beacon(
            id = UUID.randomUUID(),
            gridId = state.grid.id,
            linkId = device.mac,
            xCoordinate = coords.x,
            yCoordinate = coords.y
        )

        viewModelScope.launch {
            beaconRepository.insertBeacons(listOf(beacon))
            viewState.update {
                state.copy(
                    beacons = state.beacons + beacon,
                    showCreateBeaconDialog = false,
                    temporaryCoordinates = null
                )
            }
        }
    }

    private fun filterUnlinkedDevices(
        devices: List<BleDevice>,
        beacons: List<Beacon>
    ): List<BleDevice> {
        val linked = beacons.map { it.linkId }.toSet()
        return devices.filterNot { it.mac in linked }
    }

    fun updateMode(editMode: EditMode) {
        viewState.update { (it as? GridViewState.Success)?.copy(editMode = editMode) ?: it }
    }

    fun hideBeaconDialog() = viewState.update { (it as? GridViewState.Success)?.copy(showCreateBeaconDialog = false) ?: it }
}

sealed interface GridViewState {
    data class Success(
        val grid: Grid,
        val beacons: List<Beacon>,
        val fingerPrints: List<FingerPrint>,
        val products: List<Product>,
        val editMode: EditMode,
        val temporaryCoordinates: TemporaryCoordinates? = null,
        val showCreateBeaconDialog: Boolean = false,
        val scannedDevices: List<BleDevice> = emptyList(),
    ) : GridViewState

    data object Error : GridViewState
    data object Loading : GridViewState

    fun success(): Success? = this as? Success
}

enum class EditMode {
    VIEW_MODE,
    PRODUCT_MODE,
    BEACON_MODE,
    FINGER_PRINT_MODE,
    DELETE_MODE
}

data class TemporaryCoordinates(
    val x: Float,
    val y: Float,
)