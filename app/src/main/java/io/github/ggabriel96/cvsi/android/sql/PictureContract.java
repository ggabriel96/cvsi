package io.github.ggabriel96.cvsi.android.sql;

import android.content.ContentValues;
import android.location.Location;
import android.provider.BaseColumns;

public final class PictureContract {

  private PictureContract() {
  }

  public static final class PictureEntry implements BaseColumns {
    public static final String TABLE_NAME = "picture";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_ALTITUDE = "altitude";
    public static final String COLUMN_LOCATION_ACCURACY = "locationAccuracy";
    public static final String COLUMN_LOCATION_BEARING = "locationBearing";
    public static final String COLUMN_LOCATION_PROVIDER = "locationProvider";
    public static final String COLUMN_LOCATION_TIME = "locationTime";
    public static final String COLUMN_ACCELEROMETER_X = "accelerometerX";
    public static final String COLUMN_ACCELEROMETER_Y = "accelerometerY";
    public static final String COLUMN_ACCELEROMETER_Z = "accelerometerZ";
    public static final String COLUMN_ACCELEROMETER_STATUS = "accelerometerStatus";
    public static final String COLUMN_GYROSCOPE_X = "gyroscopeX";
    public static final String COLUMN_GYROSCOPE_Y = "gyroscopeY";
    public static final String COLUMN_GYROSCOPE_Z = "gyroscopeZ";
    public static final String COLUMN_GYROSCOPE_STATUS = "gyroscopeStatus";
    public static final String COLUMN_ROTATION_VECTOR_X = "rotationX";
    public static final String COLUMN_ROTATION_VECTOR_Y = "rotationY";
    public static final String COLUMN_ROTATION_VECTOR_Z = "rotationZ";
    public static final String COLUMN_ROTATION_COSINE = "rotationCosine";
    public static final String COLUMN_ROTATION_ACCURACY = "rotationAccuracy";
    public static final String COLUMN_ROTATION_STATUS = "rotationStatus";
    public static final String COLUMN_AZIMUTH = "azimuth";
    public static final String COLUMN_PITCH = "pitch";
    public static final String COLUMN_ROLL = "roll";

    public static ContentValues getContentValues(
        String path
      , Location location
      , float[] accelerometerValues, int accelerometerStatus
      , float[] gyroscopeValues, int gyroscopeStatus
        , float[] rotationValues, int rotationStatus
        , float[] orientationValues) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(PictureEntry.COLUMN_PATH, path);
      contentValues.put(PictureEntry.COLUMN_LATITUDE, location.getLatitude());
      contentValues.put(PictureEntry.COLUMN_LONGITUDE, location.getLongitude());
      contentValues.put(PictureEntry.COLUMN_ALTITUDE, location.getAltitude());
      contentValues.put(PictureEntry.COLUMN_LOCATION_ACCURACY, location.getAccuracy());
      contentValues.put(PictureEntry.COLUMN_LOCATION_BEARING, location.getBearing());
      contentValues.put(PictureEntry.COLUMN_LOCATION_PROVIDER, location.getProvider());
      contentValues.put(PictureEntry.COLUMN_LOCATION_TIME, location.getTime());
      contentValues.put(PictureEntry.COLUMN_ACCELEROMETER_X, accelerometerValues[0]);
      contentValues.put(PictureEntry.COLUMN_ACCELEROMETER_Y, accelerometerValues[1]);
      contentValues.put(PictureEntry.COLUMN_ACCELEROMETER_Z, accelerometerValues[2]);
      contentValues.put(PictureEntry.COLUMN_ACCELEROMETER_STATUS, accelerometerStatus);
      contentValues.put(PictureEntry.COLUMN_GYROSCOPE_X, gyroscopeValues[0]);
      contentValues.put(PictureEntry.COLUMN_GYROSCOPE_Y, gyroscopeValues[1]);
      contentValues.put(PictureEntry.COLUMN_GYROSCOPE_Z, gyroscopeValues[2]);
      contentValues.put(PictureEntry.COLUMN_GYROSCOPE_STATUS, gyroscopeStatus);
      contentValues.put(PictureEntry.COLUMN_ROTATION_VECTOR_X, rotationValues[0]);
      contentValues.put(PictureEntry.COLUMN_ROTATION_VECTOR_Y, rotationValues[1]);
      contentValues.put(PictureEntry.COLUMN_ROTATION_VECTOR_Z, rotationValues[2]);
      contentValues.put(PictureEntry.COLUMN_ROTATION_COSINE, rotationValues[3]);
      contentValues.put(PictureEntry.COLUMN_ROTATION_ACCURACY, rotationValues[4]);
      contentValues.put(PictureEntry.COLUMN_ROTATION_STATUS, rotationStatus);
      contentValues.put(PictureEntry.COLUMN_AZIMUTH, orientationValues[0]);
      contentValues.put(PictureEntry.COLUMN_PITCH, orientationValues[1]);
      contentValues.put(PictureEntry.COLUMN_ROLL, orientationValues[2]);
      return contentValues;
    }
  }

}
