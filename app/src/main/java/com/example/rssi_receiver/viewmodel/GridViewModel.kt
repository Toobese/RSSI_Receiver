package com.example.rssi_receiver.viewmodel

import android.util.Log
import android.widget.GridView
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rssi_receiver.core.model.Grid
import com.example.rssi_receiver.repository.GridRepository
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
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val viewState: MutableStateFlow<GridViewState> = MutableStateFlow(GridViewState.Loading)
    private val gridId: UUID = UUID.fromString(requireNotNull(savedStateHandle["gridId"]))

    init {
        Log.d(TAG, "initializing viewmodel")
        viewModelScope.launch {
            viewState.update {
                val grid = gridRepository.getGridById(gridId)
                val products = productRepository.getProductByGridID(gridId)
                it.success()?: GridViewState.Success(grid = grid)
            }
        }
    }
}

sealed interface GridViewState {
    data class Success(
        val grid: Grid,
    ) : GridViewState

    data object Error : GridViewState
    data object Loading : GridViewState

    fun success(): Success? = this as? Success
}