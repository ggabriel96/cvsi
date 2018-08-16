package io.github.ggabriel96.cvsi

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.location.Location
import android.util.Log
import com.google.android.gms.location.*

class LocationListener(private val activity: Activity) : LifecycleObserver {

    private val tag = this.javaClass.simpleName

    private var locationCallback: LocationCallback
    private var locationClient: FusedLocationProviderClient
    var lastLocation: Location? = null
        private set

    init {
        Log.d(tag, "init")
        this.locationClient = LocationServices.getFusedLocationProviderClient(this.activity)
        this.locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                lastLocation = locationResult?.lastLocation
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        Log.d(tag, "onResume")
        this.startLocationUpdates()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        Log.d(tag, "onPause")
        this.stopLocationUpdates()
    }

    private fun startLocationUpdates() {
        try {
            this.locationClient.requestLocationUpdates(
                    this.getLocationRequest(),
                    this.locationCallback,
                    null /* Looper */)
        } catch (e: SecurityException) {
            Log.w(tag, "startLocationUpdates failed", e)
        }
    }

    private fun stopLocationUpdates() {
        this.locationClient.removeLocationUpdates(this.locationCallback)
    }

    private fun getLocationRequest(): LocationRequest {
        return LocationRequest().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

}
