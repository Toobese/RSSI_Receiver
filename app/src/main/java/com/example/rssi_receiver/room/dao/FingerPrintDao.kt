package com.example.rssi_receiver.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rssi_receiver.room.entity.FingerPrintEntity
import java.util.UUID

@Dao
interface FingerPrintDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fingerPrint: FingerPrintEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(fingerPrints: List<FingerPrintEntity>)

    @Query("SELECT * FROM finger_print WHERE gridId = :gridId")
    suspend fun getAllFingerPrintsByGridId(gridId: UUID): List<FingerPrintEntity>
}