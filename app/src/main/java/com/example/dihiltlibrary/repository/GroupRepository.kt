package com.example.dihiltlibrary.repository

import com.example.dihiltlibrary.data.AddressData
import com.example.dihiltlibrary.data.Location
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun getLastLocation(): Location?
    fun getAddressFromLocation(location: Location): Flow<AddressData?>
}