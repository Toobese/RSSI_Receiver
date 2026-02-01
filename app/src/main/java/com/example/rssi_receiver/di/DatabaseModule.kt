package com.example.rssi_receiver.di

import android.content.Context
import androidx.room.Room
import com.example.rssi_receiver.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder<AppDatabase>(context, "database").fallbackToDestructiveMigration(true).build()
}

