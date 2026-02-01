package com.example.rssi_receiver.repository

import com.example.rssi_receiver.core.model.FingerPrint
import com.example.rssi_receiver.room.dao.FingerPrintDao
import com.example.rssi_receiver.room.entity.toEntity
import com.example.rssi_receiver.room.entity.toExternal
import java.util.UUID
import javax.inject.Inject

class FingerPrintRepository @Inject constructor(private val fingerPrintDao: FingerPrintDao) {
    suspend fun insertFingerPrints(fingerPrints: List<FingerPrint>) = fingerPrintDao.insertAll(fingerPrints.map { it.toEntity() })
    suspend fun getFingerPrintsByGridId(gridId: UUID): List<FingerPrint> =
        fingerPrintDao.getAllFingerPrintsByGridId(gridId).map { it.toExternal() }
}