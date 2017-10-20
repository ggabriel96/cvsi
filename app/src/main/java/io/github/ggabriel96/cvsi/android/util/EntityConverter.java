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
import io.github.ggabriel96.cvsi.backend.myApi.model.GeoPt;
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

  public GeoPt locationToJson(android.location.Location androidLocation) throws Exception {
    GeoPt geopoint = new GeoPt();
    geopoint.setLatitude((float) androidLocation.getLatitude());
    geopoint.setLongitude((float) androidLocation.getLongitude());
    return geopoint;
  }

  public Picture pictureToJson(String title, ExifInterface exifInterface, User user, RotationAdapter rotationAdapter) {
    Picture picture = null;
    try {
      picture = new Picture();
      Long picTimestamp = exifInterface.getDateTime();
      android.location.Location closestLocation = rotationAdapter.findClosestLocation(picTimestamp);
      SensorData closestAccelerometer = rotationAdapter.findClosestSensorData(picTimestamp, Sensor.TYPE_ACCELEROMETER);
      SensorData closestGyroscope = rotationAdapter.findClosestSensorData(picTimestamp, Sensor.TYPE_GYROSCOPE);
      SensorData closestRotation = rotationAdapter.findClosestSensorData(picTimestamp, Sensor.TYPE_ROTATION_VECTOR);
      picture.setUser(user);
      picture.setCaptured(new DateTime(picTimestamp));
      picture.setDescription("Description");
      picture.setTitle(title);
      picture.setLocation(this.locationToJson(closestLocation));
      picture.setLocationAccuracy(closestLocation.getAccuracy());
      picture.setLocationBearing(closestLocation.getBearing());
      picture.setLocationProvider(closestLocation.getProvider());
      picture.setLocationTime(new DateTime(closestLocation.getTime()));
      picture.setAccelerometerX(closestAccelerometer.values[0]);
      picture.setAccelerometerY(closestAccelerometer.values[1]);
      picture.setAccelerometerZ(closestAccelerometer.values[2]);
      picture.setAccelerometerStatus(closestAccelerometer.status);
      picture.setGyroscopeX(closestGyroscope.values[0]);
      picture.setGyroscopeY(closestGyroscope.values[1]);
      picture.setGyroscopeZ(closestGyroscope.values[2]);
      picture.setGyroscopeStatus(closestGyroscope.status);
      picture.setRotationX(closestRotation.values[0]);
      picture.setRotationY(closestRotation.values[1]);
      picture.setRotationZ(closestRotation.values[2]);
      picture.setRotationCosine(closestRotation.values[3]);
      picture.setRotationStatus(closestRotation.status);
      float[] apr = closestRotation.getOrientationValues();
      picture.setAzimuth(apr[0]);
      picture.setPitch(apr[1]);
      picture.setRoll(apr[2]);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return picture;
  }

  public DateTime dateToJson(Date date) {
    return new DateTime(date);
  }

  public ExifInterface getExif(Uri uri) throws IOException {
    Log.d(TAG, uri.getEncodedPath() + "3 kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
    return new ExifInterface(uri.getPath());
  }
}
