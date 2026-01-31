package com.example.rssi_receiver

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rssi_receiver.dao.MapDao
import com.example.rssi_receiver.entity.MapEntity

@Database(entities = [MapEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mapDao(): MapDao
}