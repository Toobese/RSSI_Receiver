package com.example.rssi_receiver.di

import com.example.rssi_receiver.room.AppDatabase
import com.example.rssi_receiver.room.dao.BeaconDao
import com.example.rssi_receiver.room.dao.FingerPrintDao
import com.example.rssi_receiver.room.dao.GridDao
import com.example.rssi_receiver.room.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Singleton
    @Provides
    fun provideBeaconDao(appDatabase: AppDatabase): BeaconDao = appDatabase.beaconDao()

    @Singleton
    @Provides
    fun provideFingerPrintDao(appDatabase: AppDatabase): FingerPrintDao = appDatabase.fingerPrintDao()

    @Singleton
    @Provides
    fun provideGridDao(appDatabase: AppDatabase): GridDao = appDatabase.gridDao()

    @Singleton
    @Provides
    fun provideProductDao(appDatabase: AppDatabase): ProductDao = appDatabase.productDao()
}