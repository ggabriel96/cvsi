package io.github.ggabriel96.cvsi.android.interfaces;

import android.content.ContentValues;

/**
 * Created by gbrl on 19/07/17.
 */

public interface SQLiteListener {
  public void onInsert(ContentValues contentValues);

  public void onUpdate(ContentValues contentValues);

  public void onDelete(ContentValues contentValues);
}
