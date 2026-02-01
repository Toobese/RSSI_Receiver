package com.example.rssi_receiver.repository

import android.util.Log
import com.example.rssi_receiver.core.model.Grid
import com.example.rssi_receiver.room.dao.GridDao
import com.example.rssi_receiver.room.entity.toEntity
import com.example.rssi_receiver.room.entity.toExternal
import javax.inject.Inject

private const val TAG = "GridRepository"

class GridRepository @Inject constructor(
    private val gridDao: GridDao,
) {
    suspend fun createGrid(grid: Grid) {
        Log.d(TAG, "createGrid: $grid")
        gridDao.insert(grid.toEntity())
    }

    suspend fun getAllGrids(): List<Grid> {
        val grids = gridDao.getAllGrids()
        return grids.map { it.toExternal() }
    }
}