package com.example.dihiltlibrary.repository

import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.dihiltlibrary.data.AddressData
import com.example.dihiltlibrary.data.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.Locale
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient,
): GroupRepository {
    // 사용자 위치 특정 메서드
    override suspend fun getLastLocation(): Location? {
        return if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val location = fusedLocationClient.lastLocation.await()
            location?.let {
                Location(it.latitude, it.longitude)
            }
        } else {
            null
        }
    }

    override fun getAddressFromLocation(location: Location): Flow<AddressData?> = callbackFlow {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val geocoder = Geocoder(context, Locale.KOREA)

            geocoder.getFromLocation(location.latitude, location.longitude, 1, object: Geocoder.GeocodeListener {
                override fun onGeocode(addresses: List<Address>) {
                    if (addresses.isNotEmpty()) {
                        val address = addresses[0].getAddressLine(0)
                        trySend(AddressData(address))
                    } else {
                        trySend(null)
                    }
                    close()
                }

                override fun onError(errorMessage: String?) {
                    close(Exception(errorMessage))
                }
            })
        } else {
            val geocoder = Geocoder(context, Locale.KOREA)
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0].getAddressLine(0)
                trySend(AddressData(address))
            } else {
                trySend(null)
            }
            close()
        }
        awaitClose()
    }
}