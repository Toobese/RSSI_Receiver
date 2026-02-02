package com.example.rssi_receiver.viewmodel

import android.util.Log
import android.widget.GridView
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rssi_receiver.core.model.Beacon
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
                        it.success()?: GridViewState.Success(
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
    }

    fun onTileClick(x: Int, y: Int) {
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

    private fun handleFingerPrint(x: Int, y: Int, state: GridViewState.Success) {
        Log.d("banaan", "x: $x and y: $y")
        viewModelScope.launch {
            val fingerPrint = FingerPrint(UUID.randomUUID(), state.grid.id, x.toFloat(), y.toFloat())
            fingerPrintRepository.insertFingerPrints(listOf(fingerPrint))
            viewState.update { (it as? GridViewState.Success)?.copy(fingerPrints = it.fingerPrints + fingerPrint) ?: it }
        }
    }

    private fun handleBeacon(x: Int, y: Int, state: GridViewState.Success) {
        Log.d("banaan", "x: $x and y: $y")
    }

    private fun handleDelete(x: Int, y: Int, state: GridViewState.Success) {
        Log.d("banaan", "x: $x and y: $y")
    }

    private fun handleProduct(x: Int, y: Int, state: GridViewState.Success) {
        Log.d("banaan", "x: $x and y: $y")
    }

    fun updateMode(editMode: EditMode) {
        viewState.update { (it as? GridViewState.Success)?.copy(editMode = editMode) ?: it }
    }
}

sealed interface GridViewState {
    data class Success(
        val grid: Grid,
        val beacons: List<Beacon>,
        val fingerPrints: List<FingerPrint>,
        val products: List<Product>,
        val editMode: EditMode,
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