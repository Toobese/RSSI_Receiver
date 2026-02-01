package com.example.rssi_receiver.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rssi_receiver.room.entity.ProductEntity
import java.util.UUID

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM product WHERE gridId = :gridId")
    suspend fun getAllProductsByGridId(gridId: UUID): List<ProductEntity>
}