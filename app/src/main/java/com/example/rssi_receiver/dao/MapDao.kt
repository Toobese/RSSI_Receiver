package com.example.rssi_receiver.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rssi_receiver.entity.MapEntity
import java.util.UUID

@Dao
interface MapDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(map: MapEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(maps: List<MapEntity>)

    @Query("SELECT * FROM map")
    suspend fun getAllMaps(): List<MapEntity>

    @Query("SELECT * FROM map WHERE id = :mapId")
    suspend fun getMapByID(mapId: UUID): MapEntity?
}