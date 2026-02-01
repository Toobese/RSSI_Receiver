package com.example.rssi_receiver.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rssi_receiver.entity.ProductEntity
import java.util.UUID

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM product WHERE mapId = :mapId")
    suspend fun getAllProductsByMapId(mapId: UUID): List<ProductEntity>
}