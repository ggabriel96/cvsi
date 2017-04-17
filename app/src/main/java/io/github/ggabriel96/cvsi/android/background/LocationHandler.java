package io.github.ggabriel96.cvsi.android.background;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * https://developers.google.com/android/reference/com/google/android/gms/location/FusedLocationProviderApi
 * https://developers.google.com/android/reference/com/google/android/gms/common/api/PendingResult
 * https://developers.google.com/android/reference/com/google/android/gms/common/api/Status
 */

public class LocationHandler implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

  private static final String LOG_TAG = "LocationHandler";
  private Context context;
  private Location lastLocation;
  private GoogleApiClient googleApiClient;
  private LocationRequest locationRequest;
  private LocationListener locationListener;
  private Boolean requestLocationUpdates, isRequestingLocationUpdates;

  public LocationHandler(Context context) {
    this.context = context;
    this.requestLocationUpdates = Boolean.FALSE;
    this.isRequestingLocationUpdates = Boolean.FALSE;

    this.locationRequest = new LocationRequest();
    this.locationRequest.setInterval(10000);
    this.locationRequest.setFastestInterval(5000);
    this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
  }

  public LocationHandler(Context context, LocationListener locationListener) {
    this(context);
    this.locationListener = locationListener;
  }

  public void build() {
    Log.d(LOG_TAG, "build");
    if (this.googleApiClient == null) {
      this.googleApiClient = new GoogleApiClient.Builder(this.context)
          .addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .addApi(LocationServices.API)
          .build();
    }
  }

  /**
   * Calls the connect method on GoogleApiClient
   *
   * @param requestLocationUpdates if location updates should be requested upon successful connection
   */
  public void connect(Boolean requestLocationUpdates) {
    Log.d(LOG_TAG, "connect");
    this.requestLocationUpdates = requestLocationUpdates;
    this.googleApiClient.connect();
  }

  public void disconnect() {
    Log.d(LOG_TAG, "disconnect");
    this.googleApiClient.disconnect();
  }

  public Boolean isRequestingLocationUpdates() {
    return this.isRequestingLocationUpdates;
  }

  /**
   * Attempts to stop location updates if the GoogleApiClient is connected and
   * <code>isRequestingLocationUpdates</code> is <code>Boolean.FALSE</code>. Upon
   * the result of the request, sets <code>isRequestingLocationUpdates</code> to
   * the result status (currently, the only way of knowing the attempt was
   * successful is later checking if this variable is <code>Boolean.TRUE</code>).
   *
   * @return <code>Boolean.TRUE</code> if the GoogleApiClient is connected and this
   * wasn't already requesting location updates. <code>Boolean.FALSE</code> otherwise
   * @throws SecurityException if the user hasn't given the app the location permission
   * @TODO provide a method that receives a ResultCallback
   * @TODO check for location permission just as I do with camera permission
   */
  public Boolean startLocationUpdates() throws SecurityException {
    Log.d(LOG_TAG, "startLocationUpdates");
    Log.d(LOG_TAG, "startLocationUpdates isConnected: " + Boolean.toString(this.googleApiClient.isConnected()));
    Log.d(LOG_TAG, "startLocationUpdates isRequestingLocationUpdates: " + Boolean.toString(this.isRequestingLocationUpdates));
    if (this.googleApiClient.isConnected() && !this.isRequestingLocationUpdates) {
      PendingResult<Status> pendingStatus = null;
      if (this.locationListener != null) {
        pendingStatus = LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApiClient, this.locationRequest, this.locationListener);
      } else {
        pendingStatus = LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApiClient, this.locationRequest, this);
      }
      pendingStatus.setResultCallback(new ResultCallback<Status>() {
        @Override
        public void onResult(@NonNull Status status) {
          LocationHandler.this.isRequestingLocationUpdates = status.isSuccess();
        }
      });
      Log.d(LOG_TAG, "startLocationUpdates returned Boolean.TRUE");
      return Boolean.TRUE;
    }
    Log.d(LOG_TAG, "startLocationUpdates returned Boolean.FALSE");
    return Boolean.FALSE;
  }

  /**
   * Attempts to stop location updates if the GoogleApiClient is connected and
   * <code>isRequestingLocationUpdates</code> is <code>Boolean.TRUE</code>. Upon
   * the result of therequest, sets <code>isRequestingLocationUpdates</code> to the
   * result status (currently, the only way of knowing the attempt was successful
   * is later checking if this variable is <code>Boolean.FALSE</code>).
   */
  public void stopLocationUpdates() {
    Log.d(LOG_TAG, "stopLocationUpdates");
    if (this.googleApiClient.isConnected() && this.isRequestingLocationUpdates) {
      PendingResult<Status> pendingStatus = null;
      if (this.locationListener != null) {
        pendingStatus = LocationServices.FusedLocationApi.removeLocationUpdates(this.googleApiClient, this.locationListener);
      } else {
        pendingStatus = LocationServices.FusedLocationApi.removeLocationUpdates(this.googleApiClient, this);
      }
      pendingStatus.setResultCallback(new ResultCallback<Status>() {
        @Override
        public void onResult(@NonNull Status status) {
          LocationHandler.this.isRequestingLocationUpdates = !status.isSuccess();
        }
      });
    }
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) throws SecurityException {
    Log.d(LOG_TAG, "onConnected");
    this.lastLocation = LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
    if (this.lastLocation != null) {
      Log.d(LOG_TAG, this.lastLocation.toString());
    }
    if (this.requestLocationUpdates) {
      this.startLocationUpdates();
    }
  }

  @Override
  public void onConnectionSuspended(int i) {
    Log.w(LOG_TAG, "onConnectionSuspended: " + Integer.toString(i));
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Log.e(LOG_TAG, "onConnectionSuspended: " + connectionResult.getErrorMessage());
  }

  /**
   * @TODO remove when LocationListener becomes mandatory
   */
  @Override
  public void onLocationChanged(Location location) {
    Log.d(LOG_TAG, "onLocationChanged");
    this.lastLocation = location;
    Log.d(LOG_TAG, this.lastLocationToString());
    Toast.makeText(this.context, "Location accuracy: " + String.valueOf(location.getAccuracy()) + "m", Toast.LENGTH_SHORT).show();
  }

  public String lastLocationToString() {
    if (this.lastLocation != null) {
      return this.locationToString(this.lastLocation);
    } else {
      return "Last location is not known.";
    }
  }

  public String locationToString(Location location) {
    StringBuffer sb = new StringBuffer("Location(").append(location.getLatitude()).append(", ").append(location.getLongitude())
        .append(", ").append(location.getAltitude()).append(") provided by ").append(location.getProvider());
    if (location.getExtras() != null) {
      Object satellites = location.getExtras().get("satellites");
      if (satellites != null) sb.append(" with ").append(satellites).append(" satellites");
    }
    sb.append(" at timestamp ").append(location.getTime())
        .append(" with an accuracy of ").append(location.getAccuracy())
        .append("m and a bearing of ").append(location.getBearing()).append(" degrees.");
    return sb.toString();
  }

  public Location getLastLocation() {
    return lastLocation;
  }
}
