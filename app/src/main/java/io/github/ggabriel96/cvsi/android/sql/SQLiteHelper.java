package io.github.ggabriel96.cvsi.android.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

  private static final String TAG = "SQLiteHelper";

  private static final int DATABASE_VERSION = 2;
  private static final String DATABASE_NAME = "cvsi.db";
  private static final String CREATE_TABLE_ALBUM
    = "create table " + SQLiteContract.AlbumEntry.TABLE_NAME + "("
    + SQLiteContract.AlbumEntry.COLUMN_TITLE + " text primary key,"
    + SQLiteContract.AlbumEntry.COLUMN_COVER + " text,"
    + SQLiteContract.AlbumEntry.COLUMN_DESCRIPTION + " text,"
    + SQLiteContract.AlbumEntry.COLUMN_CREATION_DATE + " integer,"
    + SQLiteContract.AlbumEntry.COLUMN_MODIFY_DATE + " integer,"
    + "foreign key (" + SQLiteContract.AlbumEntry.COLUMN_COVER + ") references "+ SQLiteContract.PictureEntry.TABLE_NAME + "(" + SQLiteContract.PictureEntry.COLUMN_PATH + ")"
    + ");";

  private static final String CREATE_TABLE_PICTURE
      = "create table " + SQLiteContract.PictureEntry.TABLE_NAME + "("
      + SQLiteContract.PictureEntry.COLUMN_PATH + " text primary key,"
      + SQLiteContract.PictureEntry.COLUMN_LATITUDE + " real,"
      + SQLiteContract.PictureEntry.COLUMN_LONGITUDE + " real,"
      + SQLiteContract.PictureEntry.COLUMN_LOCATION_ACCURACY + " real,"
      + SQLiteContract.PictureEntry.COLUMN_LOCATION_BEARING + " real,"
      + SQLiteContract.PictureEntry.COLUMN_LOCATION_PROVIDER + " text,"
      + SQLiteContract.PictureEntry.COLUMN_LOCATION_TIME + " integer,"
      + SQLiteContract.PictureEntry.COLUMN_ACCELEROMETER_X + " real,"
      + SQLiteContract.PictureEntry.COLUMN_ACCELEROMETER_Y + " real,"
      + SQLiteContract.PictureEntry.COLUMN_ACCELEROMETER_Z + " real,"
      + SQLiteContract.PictureEntry.COLUMN_ACCELEROMETER_STATUS + " integer,"
      + SQLiteContract.PictureEntry.COLUMN_GYROSCOPE_X + " real,"
      + SQLiteContract.PictureEntry.COLUMN_GYROSCOPE_Y + " real,"
      + SQLiteContract.PictureEntry.COLUMN_GYROSCOPE_Z + " real,"
      + SQLiteContract.PictureEntry.COLUMN_GYROSCOPE_STATUS + " integer,"
      + SQLiteContract.PictureEntry.COLUMN_ROTATION_VECTOR_X + " real,"
      + SQLiteContract.PictureEntry.COLUMN_ROTATION_VECTOR_Y + " real,"
      + SQLiteContract.PictureEntry.COLUMN_ROTATION_VECTOR_Z + " real,"
      + SQLiteContract.PictureEntry.COLUMN_ROTATION_COSINE + " real,"
      + SQLiteContract.PictureEntry.COLUMN_ROTATION_STATUS + " integer"
      + SQLiteContract.PictureEntry.COLUMN_AZIMUTH + " real,"
      + SQLiteContract.PictureEntry.COLUMN_PITCH + " real,"
      + SQLiteContract.PictureEntry.COLUMN_ROLL + " real,"
    + SQLiteContract.PictureEntry.COLUMN_ALBUM + " text,"
    + SQLiteContract.PictureEntry.COLUMN_DESCRIPTION + " text,"
    + SQLiteContract.PictureEntry.COLUMN_TITLE + " text,"
      + "foreign key (" + SQLiteContract.PictureEntry.COLUMN_ALBUM + ") references "+ SQLiteContract.AlbumEntry.TABLE_NAME + "(" + SQLiteContract.AlbumEntry.COLUMN_TITLE + ")"
      + ");";

  public SQLiteHelper(Context context) {
    super(context, SQLiteHelper.DATABASE_NAME, null, SQLiteHelper.DATABASE_VERSION);
  }

  @Override
  public void onOpen(SQLiteDatabase db) {
    super.onOpen(db);
    db.execSQL("PRAGMA foreign_keys = ON");
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQLiteHelper.CREATE_TABLE_ALBUM);
    Log.d(TAG, SQLiteHelper.CREATE_TABLE_ALBUM);
    db.execSQL(SQLiteHelper.CREATE_TABLE_PICTURE);
    Log.d(TAG, SQLiteHelper.CREATE_TABLE_PICTURE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.d(TAG, "onUpgrade from version " + String.valueOf(oldVersion) + " to " + String.valueOf(newVersion));
//    switch (newVersion) {
//      case 2:
//        db.execSQL("alter table " + SQLiteContract.PictureEntry.TABLE_NAME + " add column " + SQLiteContract.PictureEntry.COLUMN_AZIMUTH + " real;");
//        db.execSQL("alter table " + SQLiteContract.PictureEntry.TABLE_NAME + " add column " + SQLiteContract.PictureEntry.COLUMN_PITCH + " real;");
//        db.execSQL("alter table " + SQLiteContract.PictureEntry.TABLE_NAME + " add column " + SQLiteContract.PictureEntry.COLUMN_ROLL + " real;");
//    }
  }
}
