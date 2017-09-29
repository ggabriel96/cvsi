package io.github.ggabriel96.cvsi.android.sql;

import android.content.ContentValues;
import android.provider.BaseColumns;

import java.util.Date;

import io.github.ggabriel96.cvsi.backend.myApi.model.Picture;


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
    public static final String COLUMN_ALBUM = "album";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TITLE = "title";
    //public static final String COLUMN_USER = "user";


    public static ContentValues getContentValues(String path, Picture picture, String album) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(PictureEntry.COLUMN_PATH, path);
      contentValues.put(PictureEntry.COLUMN_LATITUDE, picture.getLocation().getLatitude());
      contentValues.put(PictureEntry.COLUMN_LONGITUDE, picture.getLocation().getLongitude());
      contentValues.put(PictureEntry.COLUMN_LOCATION_ACCURACY, picture.getLocationAccuracy());
      contentValues.put(PictureEntry.COLUMN_LOCATION_BEARING, picture.getLocationBearing());
      contentValues.put(PictureEntry.COLUMN_LOCATION_PROVIDER, picture.getLocationProvider());
      contentValues.put(PictureEntry.COLUMN_LOCATION_TIME, picture.getLocationTime().getValue());
      contentValues.put(PictureEntry.COLUMN_ACCELEROMETER_X, picture.getAccelerometerX());
      contentValues.put(PictureEntry.COLUMN_ACCELEROMETER_Y, picture.getAccelerometerY());
      contentValues.put(PictureEntry.COLUMN_ACCELEROMETER_Z, picture.getAccelerometerZ());
      contentValues.put(PictureEntry.COLUMN_ACCELEROMETER_STATUS, picture.getAccelerometerStatus());
      contentValues.put(PictureEntry.COLUMN_GYROSCOPE_X, picture.getGyroscopeX());
      contentValues.put(PictureEntry.COLUMN_GYROSCOPE_Y, picture.getGyroscopeY());
      contentValues.put(PictureEntry.COLUMN_GYROSCOPE_Z, picture.getGyroscopeZ());
      contentValues.put(PictureEntry.COLUMN_GYROSCOPE_STATUS, picture.getGyroscopeStatus());
      contentValues.put(PictureEntry.COLUMN_ROTATION_VECTOR_X, picture.getRotationX());
      contentValues.put(PictureEntry.COLUMN_ROTATION_VECTOR_Y, picture.getRotationY());
      contentValues.put(PictureEntry.COLUMN_ROTATION_VECTOR_Z, picture.getRotationZ());
      contentValues.put(PictureEntry.COLUMN_ROTATION_COSINE, picture.getRotationCosine());
      contentValues.put(PictureEntry.COLUMN_ROTATION_STATUS, picture.getRotationStatus());
      contentValues.put(PictureEntry.COLUMN_AZIMUTH, picture.getAzimuth());
      contentValues.put(PictureEntry.COLUMN_PITCH, picture.getPitch());
      contentValues.put(PictureEntry.COLUMN_ROLL, picture.getRoll());
      contentValues.put(PictureEntry.COLUMN_ALBUM, album);
      contentValues.put(PictureEntry.COLUMN_DESCRIPTION, picture.getDescription());
      contentValues.put(PictureEntry.COLUMN_TITLE, picture.getTitle());
      return contentValues;
    }
  }

}
