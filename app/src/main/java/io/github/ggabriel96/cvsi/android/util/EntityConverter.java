package io.github.ggabriel96.cvsi.android.util;

import android.net.Uri;
import android.util.Log;

import com.google.api.client.util.DateTime;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

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

  public Picture pictureToJson(Uri uri) {
    Picture picture = new Picture();
    picture.setCaptured(this.dateToJson(new Date()));
    picture.setDescription("Description");
    picture.setTitle(uri.getLastPathSegment());
    return picture;
  }

  public DateTime dateToJson(Date date) {
    return new DateTime(date);
  }

}
