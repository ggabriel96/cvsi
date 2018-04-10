package io.github.ggabriel96.cvsi.android.sql;

import android.content.ContentValues;
import android.hardware.Sensor;
import android.provider.BaseColumns;
import android.support.media.ExifInterface;

import java.util.Date;

import io.github.ggabriel96.cvsi.android.controller.RotationAdapter;
import io.github.ggabriel96.cvsi.android.model.SensorData;


public final class SQLiteContract {

  private SQLiteContract() {
  }

  public static final class AlbumEntry implements BaseColumns {
    public static final String TABLE_NAME = "album";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COVER = "cover";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CREATION_DATE = "creationDate";
    public static final String COLUMN_MODIFY_DATE = "modifyDate";

    public static ContentValues getInsertContentValues(String title, String description, Date creationDate) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(AlbumEntry.COLUMN_TITLE, title);
      contentValues.put(AlbumEntry.COLUMN_DESCRIPTION, description);
      contentValues.put(AlbumEntry.COLUMN_CREATION_DATE, creationDate.getTime());
      contentValues.put(AlbumEntry.COLUMN_MODIFY_DATE, creationDate.getTime());
      return contentValues;
    }
    public static ContentValues getUpdateDateContentValues(Date modifyDate) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(AlbumEntry.COLUMN_MODIFY_DATE, modifyDate.getTime());
      return contentValues;
    }

    public static ContentValues getUpdateDescriptionContentValues(String description) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(AlbumEntry.COLUMN_DESCRIPTION, description);
      return contentValues;
    }

    public static ContentValues getUpdateCoverContentValues(String path) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(AlbumEntry.COLUMN_COVER, path);
      return contentValues;
    }
  }

  public static final class PictureEntry implements BaseColumns {
    public static final String TABLE_NAME = "picture";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
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
    public static final String COLUMN_ROTATION_STATUS = "rotationStatus";
    public static final String COLUMN_AZIMUTH = "azimuth";
    public static final String COLUMN_PITCH = "pitch";
    public static final String COLUMN_ROLL = "roll";
    public static final String COLUMN_TITLE = "title";

    public static ContentValues getContentValues(String path, String title, ExifInterface exifInterface, RotationAdapter rotationAdapter) {
      ContentValues contentValues = new ContentValues();
      try {
        Long picTimestamp = Long.valueOf(exifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL));
        android.location.Location closestLocation = rotationAdapter.findClosestLocation(picTimestamp);
        SensorData closestAccelerometer = rotationAdapter.findClosestSensorData(picTimestamp, Sensor.TYPE_ACCELEROMETER);
        SensorData closestGyroscope = rotationAdapter.findClosestSensorData(picTimestamp, Sensor.TYPE_GYROSCOPE);
        SensorData closestRotation = rotationAdapter.findClosestSensorData(picTimestamp, Sensor.TYPE_ROTATION_VECTOR);
        contentValues.put(PictureEntry.COLUMN_PATH, path);
        contentValues.put(PictureEntry.COLUMN_LATITUDE, closestLocation.getLatitude());
        contentValues.put(PictureEntry.COLUMN_LONGITUDE, closestLocation.getLongitude());
        contentValues.put(PictureEntry.COLUMN_LOCATION_ACCURACY, closestLocation.getAccuracy());
        contentValues.put(PictureEntry.COLUMN_LOCATION_BEARING, closestLocation.getBearing());
        contentValues.put(PictureEntry.COLUMN_LOCATION_PROVIDER, closestLocation.getProvider());
        contentValues.put(PictureEntry.COLUMN_LOCATION_TIME, closestLocation.getTime());
        contentValues.put(PictureEntry.COLUMN_ACCELEROMETER_X, closestAccelerometer.values[0]);
        contentValues.put(PictureEntry.COLUMN_ACCELEROMETER_Y, closestAccelerometer.values[1]);
        contentValues.put(PictureEntry.COLUMN_ACCELEROMETER_Z, closestAccelerometer.values[2]);
        contentValues.put(PictureEntry.COLUMN_ACCELEROMETER_STATUS, closestAccelerometer.status);
        contentValues.put(PictureEntry.COLUMN_GYROSCOPE_X, closestGyroscope.values[0]);
        contentValues.put(PictureEntry.COLUMN_GYROSCOPE_Y, closestGyroscope.values[1]);
        contentValues.put(PictureEntry.COLUMN_GYROSCOPE_Z, closestGyroscope.values[2]);
        contentValues.put(PictureEntry.COLUMN_GYROSCOPE_STATUS, closestGyroscope.status);
        contentValues.put(PictureEntry.COLUMN_ROTATION_VECTOR_X, closestGyroscope.values[0]);
        contentValues.put(PictureEntry.COLUMN_ROTATION_VECTOR_Y, closestGyroscope.values[1]);
        contentValues.put(PictureEntry.COLUMN_ROTATION_VECTOR_Z, closestGyroscope.values[2]);
        contentValues.put(PictureEntry.COLUMN_ROTATION_COSINE, closestRotation.values[3]);
        contentValues.put(PictureEntry.COLUMN_ROTATION_STATUS, closestRotation.status);
        float[] apr = closestRotation.getOrientationValues();
        contentValues.put(PictureEntry.COLUMN_AZIMUTH, apr[0]);
        contentValues.put(PictureEntry.COLUMN_PITCH, apr[1]);
        contentValues.put(PictureEntry.COLUMN_ROLL, apr[2]);
        contentValues.put(PictureEntry.COLUMN_TITLE, title);

      } catch (Exception e) {
        e.printStackTrace();
      }
      return contentValues;
    }
  }

}
