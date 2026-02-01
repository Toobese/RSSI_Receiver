package com.example.rssi_receiver.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rssi_receiver.room.entity.BeaconEntity
import java.util.UUID

@Dao
interface BeaconDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(beacon: BeaconEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(beacons: List<BeaconEntity>)

    @Query("SELECT * FROM beacon WHERE mapId = :mapID")
    suspend fun getAllBeaconsByMapId(mapID: UUID): List<BeaconEntity>
}