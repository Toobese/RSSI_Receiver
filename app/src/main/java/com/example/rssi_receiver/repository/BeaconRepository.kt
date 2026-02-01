package com.example.rssi_receiver.repository

import com.example.rssi_receiver.core.model.Beacon
import com.example.rssi_receiver.room.dao.BeaconDao
import com.example.rssi_receiver.room.entity.toEntity
import com.example.rssi_receiver.room.entity.toExternal
import java.util.UUID
import javax.inject.Inject

class BeaconRepository @Inject constructor(private val beaconDao: BeaconDao) {
    suspend fun insertBeacons(beacons: List<Beacon>) = beaconDao.insertAll(beacons.map { it.toEntity() })
    suspend fun getBeaconsByGridId(gridId: UUID): List<Beacon> = beaconDao.getAllBeaconsByGridId(gridId).map { it.toExternal() }
}