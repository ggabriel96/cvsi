package io.github.ggabriel96.cvsi.android.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "cvsi.db";
  private static final String CREATE_TABLE_PICTURE
    = "create table " + PictureContract.PictureEntry.TABLE_NAME + "("
    + PictureContract.PictureEntry.COLUMN_PATH + " text primary key,"
    + PictureContract.PictureEntry.COLUMN_ROTATION_VECTOR_X + " real,"
    + PictureContract.PictureEntry.COLUMN_ROTATION_VECTOR_Y + " real,"
    + PictureContract.PictureEntry.COLUMN_ROTATION_VECTOR_Z + " real"
    + PictureContract.PictureEntry.COLUMN_ROTATION_COSINE + " real"
    + PictureContract.PictureEntry.COLUMN_ROTATION_RAD_ACCURACY + " real"
    + ");";

  public SQLiteHelper(Context context) {
    super(context, SQLiteHelper.DATABASE_NAME, null, SQLiteHelper.DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQLiteHelper.CREATE_TABLE_PICTURE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}
