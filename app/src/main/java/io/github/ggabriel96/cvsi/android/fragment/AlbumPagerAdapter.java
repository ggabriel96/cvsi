package io.github.ggabriel96.cvsi.android.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.github.ggabriel96.cvsi.android.sql.SQLiteContract;
import io.github.ggabriel96.cvsi.android.sql.SQLiteHelper;

/**
 * Created by gbrl on 10/07/17.
 */

public class AlbumPagerAdapter extends FragmentPagerAdapter {

  private OrderBy orderBy;
  private OrderDirection orderDirection;

  private SQLiteHelper sqLiteHelper;
  private SQLiteDatabase sqLiteDatabase;

  private List<Album> albums;

  public AlbumPagerAdapter(Context context, FragmentManager fm, OrderBy orderBy) {
    this(context,fm,orderBy,OrderDirection.ASC);

  }

  public AlbumPagerAdapter(Context context,FragmentManager fm,OrderBy orderBy,OrderDirection orderDirection) {
    super(fm);
    this.orderBy = orderBy;
    this.orderDirection = orderDirection;

    this.sqLiteHelper = new SQLiteHelper(context);
    this.sqLiteDatabase = this.sqLiteHelper.getWritableDatabase();
    //https://developer.android.com/training/basics/data-storage/databases.html#WriteDbRow
    this.albums = this.getAlbumsFromDB();
  }

  @Override
  public Fragment getItem(int position) {
    return this.albums.get(position);
  }

  @Override
  public int getCount() {
    return this.albums.size();
  }

  private List<Album> getAlbumsFromDB() {

// Define a projection that specifies which columns from the database
// you will actually use after this query.
    String[] projection = {
      SQLiteContract.AlbumEntry.COLUMN_TITLE,
      SQLiteContract.AlbumEntry.COLUMN_COVER,
      SQLiteContract.AlbumEntry.COLUMN_DESCRIPTION,
      SQLiteContract.AlbumEntry.COLUMN_CREATION_DATE,
      SQLiteContract.AlbumEntry.COLUMN_MODIFY_DATE
    };

// How you want the results sorted in the resulting Cursor
    String sortOrder = null;

    if (this.orderBy == OrderBy.CREATION_DATE)
      sortOrder = SQLiteContract.AlbumEntry.COLUMN_CREATION_DATE;
    else if (this.orderBy == OrderBy.MODIFY_DATE)
      sortOrder = SQLiteContract.AlbumEntry.COLUMN_MODIFY_DATE;
    else sortOrder = SQLiteContract.AlbumEntry.COLUMN_TITLE;

    if (this.orderDirection == OrderDirection.DESC) sortOrder += " DESC";
    else sortOrder += " ASC";

    Cursor cursor = this.sqLiteDatabase.query(
      SQLiteContract.AlbumEntry.TABLE_NAME,                     // The table to query
      projection,                               // The columns to return
      null,                                // The columns for the WHERE clause
      null,                            // The values for the WHERE clause
      null,                                     // don't group the rows
      null,                                     // don't filter by row groups
      sortOrder                                 // The sort order
    );

    List<Album> list = new ArrayList<>();
    while (cursor.moveToNext()) {
      String albumTitle = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteContract.AlbumEntry.COLUMN_TITLE));
      String albumCover = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteContract.AlbumEntry.COLUMN_COVER));
      String albumDescription = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteContract.AlbumEntry.COLUMN_DESCRIPTION));
      Date albumCreationDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(SQLiteContract.AlbumEntry.COLUMN_CREATION_DATE)));
      Date albumModifyDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(SQLiteContract.AlbumEntry.COLUMN_MODIFY_DATE)));
      list.add(Album.newInstance(albumTitle, albumCover, albumDescription, albumCreationDate, albumModifyDate));
    }
    cursor.close();
    return list;

  }

  enum OrderBy {
    TITLE, CREATION_DATE, MODIFY_DATE
  }

  enum OrderDirection {
    ASC, DESC
  }
}
