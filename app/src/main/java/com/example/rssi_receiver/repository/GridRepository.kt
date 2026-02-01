package com.example.rssi_receiver.repository

import com.example.rssi_receiver.core.model.Grid
import com.example.rssi_receiver.room.dao.GridDao
import com.example.rssi_receiver.room.entity.toEntity
import com.example.rssi_receiver.room.entity.toExternal
import java.util.UUID
import javax.inject.Inject

class GridRepository @Inject constructor(private val gridDao: GridDao) {
    suspend fun insertGrids(grids: List<Grid>) = gridDao.insertAll(grids.map { it.toEntity() })
    suspend fun getAllGrids(): List<Grid> = gridDao.getAllGrids().map { it.toExternal() }
    suspend fun getGridById(gridId: UUID): Grid? = gridDao.getGridByID(gridId)?.toExternal()
}