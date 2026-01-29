package com.example.rssi_receiver

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rssi_receiver.core.model.Map

@Database(entities = [Map::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mapDao(): MapDao
}