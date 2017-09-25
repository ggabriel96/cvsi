package io.github.ggabriel96.cvsi.android.util;

import android.hardware.Sensor;
import android.net.Uri;
import android.support.media.ExifInterface;
import android.util.Log;

import com.google.api.client.util.DateTime;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Date;

import io.github.ggabriel96.cvsi.android.controller.RotationAdapter;
import io.github.ggabriel96.cvsi.android.model.SensorData;
import io.github.ggabriel96.cvsi.backend.myApi.model.Location;
import io.github.ggabriel96.cvsi.backend.myApi.model.Picture;
import io.github.ggabriel96.cvsi.backend.myApi.model.User;

public class EntityConverter {

  private static final String TAG = "EntityConverter";

  public User userToJson(FirebaseUser firebaseUser) {
    User user = new User();
    Log.d(TAG, "Uid: " + firebaseUser.getUid());
    user.setUid(firebaseUser.getUid());
    user.setEmail(firebaseUser.getEmail());
    user.setUsername(firebaseUser.getDisplayName());
    return user;
  }

  public Location locationToJson(android.location.Location androidLocation) {
    Location location = new Location();
    location.setId(1L);
    location.setAltitude(androidLocation.getAltitude());
    location.setLatitude(androidLocation.getLatitude());
    location.setLongitude(androidLocation.getLongitude());
    location.setLocationAccuracy(androidLocation.getAccuracy());
    location.setLocationBearing(androidLocation.getBearing());
    location.setLocationProvider(androidLocation.getProvider());
    location.setLocationTime(androidLocation.getTime());
    return location;
  }

  public Picture pictureToJson(Uri uri, RotationAdapter rotationAdapter) {
    Picture picture = null;
    try {
      picture = new Picture();
      ExifInterface exifInterface = this.getExif(uri);
      Long picTimestamp = exifInterface.getDateTime();
      android.location.Location closestLocation = rotationAdapter.findClosestLocation(picTimestamp);
      SensorData closestAccelerometer = rotationAdapter.findClosestSensorData(picTimestamp, Sensor.TYPE_ACCELEROMETER);
      SensorData closestGyroscope = rotationAdapter.findClosestSensorData(picTimestamp, Sensor.TYPE_GYROSCOPE);
      SensorData closestRotation = rotationAdapter.findClosestSensorData(picTimestamp, Sensor.TYPE_ROTATION_VECTOR);
      picture.setCaptured(new DateTime(picTimestamp));
      picture.setDescription("Description");
      picture.setTitle(uri.getLastPathSegment());
      picture.setLocation(this.locationToJson(closestLocation));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return picture;
  }

  public DateTime dateToJson(Date date) {
    return new DateTime(date);
  }

  public ExifInterface getExif(Uri uri) throws IOException {
    return new ExifInterface(uri.getPath());
  }
}
