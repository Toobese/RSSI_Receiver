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
                        )
                    }
                }
            }
        }
    }
}

sealed interface GridViewState {
    data class Success(
        val grid: Grid,
        val beacons: List<Beacon>,
        val fingerPrints: List<FingerPrint>,
        val products: List<Product>,
    ) : GridViewState

    data object Error : GridViewState
    data object Loading : GridViewState

    fun success(): Success? = this as? Success
}