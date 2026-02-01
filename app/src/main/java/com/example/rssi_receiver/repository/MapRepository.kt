package com.example.rssi_receiver.repository

import android.util.Log
import com.example.rssi_receiver.core.model.Map
import com.example.rssi_receiver.room.dao.MapDao
import com.example.rssi_receiver.room.entity.toEntity
import javax.inject.Inject

private const val TAG = "MapRepository"

class MapRepository @Inject constructor(
    private val mapDao: MapDao,
) {
    suspend fun createMap(map: Map) {
        Log.d(TAG, "createMap: $map")
        mapDao.insertAll(map.toEntity())
    }
}