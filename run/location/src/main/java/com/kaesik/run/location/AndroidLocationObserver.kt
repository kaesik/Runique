package com.kaesik.run.location

import android.content.Context
import android.location.LocationManager
import androidx.core.content.getSystemService
import com.google.android.gms.location.LocationServices
import com.kaesik.core.domain.location.Location
import com.kaesik.run.domain.LocationObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AndroidLocationObserver(
    private val context: Context
): LocationObserver {
    private val client = LocationServices.getFusedLocationProviderClient(context)

    override fun observeLocation(interval: Long): Flow<Location> {
        return callbackFlow {
            val locationManager = context.getSystemService<LocationManager>()!!
            var isGpsEnabled = false
            var isNetworkEnabled = false
            while (!isGpsEnabled && !isNetworkEnabled) {
                isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                if (!isGpsEnabled || !isNetworkEnabled) {
                    delay(3000L)
                }
            }

            client.lastLocation.addOnSuccessListener {
                it?.let { location ->
                    trySend(location.toLocationAlt())
                }
            }
        }
    }
}
