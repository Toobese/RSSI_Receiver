package com.example.rssi_receiver.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.rssi_receiver.entity.MapEntity

@Dao
interface MapDao {
    @Insert
    suspend fun insertAll(vararg maps: MapEntity)
}