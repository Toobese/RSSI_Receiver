package com.example.rssi_receiver.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rssi_receiver.room.dao.BeaconDao
import com.example.rssi_receiver.room.dao.FingerPrintDao
import com.example.rssi_receiver.room.dao.GridDao
import com.example.rssi_receiver.room.dao.ProductDao
import com.example.rssi_receiver.room.entity.BeaconEntity
import com.example.rssi_receiver.room.entity.FingerPrintEntity
import com.example.rssi_receiver.room.entity.GridEntity
import com.example.rssi_receiver.room.entity.ProductEntity

@Database(
    entities = [GridEntity::class, ProductEntity::class, BeaconEntity::class, FingerPrintEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gridDao(): GridDao
    abstract fun productDao(): ProductDao
    abstract fun beaconDao(): BeaconDao
    abstract fun fingerPrintDao(): FingerPrintDao
}