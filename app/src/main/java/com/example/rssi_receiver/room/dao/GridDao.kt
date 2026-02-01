package com.example.rssi_receiver.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rssi_receiver.room.entity.GridEntity
import java.util.UUID

@Dao
interface GridDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(grids: List<GridEntity>)

    @Query("SELECT * FROM grid")
    suspend fun getAllGrids(): List<GridEntity>

    @Query("SELECT * FROM grid WHERE id = :gridId")
    suspend fun getGridByID(gridId: UUID): GridEntity?
}