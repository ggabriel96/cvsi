package io.github.ggabriel96.cvsi.android.sql;

import android.content.ContentValues;
import android.provider.BaseColumns;

public final class PictureContract {

  private PictureContract() {
  }

  public static final class PictureEntry implements BaseColumns {
    public static final String TABLE_NAME = "picture";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_ROTATION_VECTOR_X = "vector_x";
    public static final String COLUMN_ROTATION_VECTOR_Y = "vector_y";
    public static final String COLUMN_ROTATION_VECTOR_Z = "vector_z";
    public static final String COLUMN_ROTATION_COSINE = "vector_cos";
    public static final String COLUMN_ROTATION_RAD_ACCURACY = "vector_acc";

    public static final ContentValues getContentValues(String path, float[] rotationVector) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(PictureEntry.COLUMN_PATH, path);
      contentValues.put(PictureEntry.COLUMN_ROTATION_VECTOR_X, rotationVector[0]);
      contentValues.put(PictureEntry.COLUMN_ROTATION_VECTOR_Y, rotationVector[1]);
      contentValues.put(PictureEntry.COLUMN_ROTATION_VECTOR_Z, rotationVector[2]);
      contentValues.put(PictureEntry.COLUMN_ROTATION_COSINE, rotationVector[3]);
      contentValues.put(PictureEntry.COLUMN_ROTATION_RAD_ACCURACY, rotationVector[4]);
      return contentValues;
    }
  }

}
