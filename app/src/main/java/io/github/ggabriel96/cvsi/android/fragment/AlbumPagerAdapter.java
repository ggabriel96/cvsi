package io.github.ggabriel96.cvsi.android.fragment;

import android.content.ContentValues;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Date;
import java.util.List;

import io.github.ggabriel96.cvsi.android.interfaces.SQLiteListener;
import io.github.ggabriel96.cvsi.android.sql.SQLiteContract;

/**
 * Created by gbrl on 10/07/17.
 */

public class AlbumPagerAdapter extends FragmentPagerAdapter implements SQLiteListener {

  private OrderBy orderBy;
  private OrderDirection orderDirection;
  private Albums albumsFragment;

  private List<Album> albums;

  public AlbumPagerAdapter(Albums a, FragmentManager fm, OrderBy orderBy) {
    this(a, fm, orderBy, OrderDirection.ASC);

  }

  public AlbumPagerAdapter(Albums a, FragmentManager fm, OrderBy orderBy, OrderDirection orderDirection) {
    super(fm);
    this.albumsFragment = a;
    this.orderBy = orderBy;
    this.orderDirection = orderDirection;
    this.albumsFragment.setSqLiteListener(this);

    //https://developer.android.com/training/basics/data-storage/databases.html#WriteDbRow
    this.albums = this.albumsFragment.getAlbumsFromDB(this.orderBy, this.orderDirection);
  }

  @Override
  public Fragment getItem(int position) {
    return this.albums.get(position);
  }

  @Override
  public int getCount() {
    return this.albums.size();
  }

  @Override
  public void onInsert(ContentValues contentValues) {
    this.albums.add(Album.newInstance(contentValues.getAsString(SQLiteContract.AlbumEntry.COLUMN_TITLE), null, contentValues.getAsString(SQLiteContract.AlbumEntry.COLUMN_DESCRIPTION),
      new Date(contentValues.getAsLong(SQLiteContract.AlbumEntry.COLUMN_CREATION_DATE)), new Date(contentValues.getAsLong(SQLiteContract.AlbumEntry.COLUMN_MODIFY_DATE))));
    this.notifyDataSetChanged();
  }

  @Override
  public void onUpdate(ContentValues contentValues) {

  }

  @Override
  public void onDelete(ContentValues contentValues) {

  }


  enum OrderBy {
    TITLE, CREATION_DATE, MODIFY_DATE
  }

  enum OrderDirection {
    ASC, DESC
  }
}
