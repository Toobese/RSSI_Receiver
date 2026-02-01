package com.example.rssi_receiver.repository

import com.example.rssi_receiver.core.model.Product
import com.example.rssi_receiver.room.dao.ProductDao
import com.example.rssi_receiver.room.entity.toEntity
import com.example.rssi_receiver.room.entity.toExternal
import java.util.UUID
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao) {
    suspend fun insertProducts(products: List<Product>) = productDao.insertAll(products.map { it.toEntity() })
    suspend fun getAllProductsByGridId(gridId: UUID): List<Product> = productDao.getAllProductsByGridId(gridId).map { it.toExternal() }
}