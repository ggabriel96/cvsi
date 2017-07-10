package io.github.ggabriel96.cvsi.android.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.github.ggabriel96.cvsi.android.sql.SQLiteHelper;

/**
 * Created by gbrl on 10/07/17.
 */

public class AlbumPagerAdapter extends FragmentPagerAdapter {

  private FragmentManager fragmentManager;
  private OrderBy orderBy;
  private OrderDirection orderDirection;
  private Context context;

  private SQLiteHelper sqLiteHelper;
  private SQLiteDatabase sqLiteDatabase;

  private List<Album> albums;

  public AlbumPagerAdapter(Context context, FragmentManager fm, OrderBy orderBy) {
    this(context,fm,orderBy,OrderDirection.ASC);

  }

  public AlbumPagerAdapter(Context context,FragmentManager fm,OrderBy orderBy,OrderDirection orderDirection) {
    super(fm);
    this.context = context;
    this.fragmentManager = fm;
    this.orderBy = orderBy;
    this.orderDirection = orderDirection;

    this.sqLiteHelper = new SQLiteHelper(this.context);
    this.sqLiteDatabase = this.sqLiteHelper.getWritableDatabase();
    //https://developer.android.com/training/basics/data-storage/databases.html#WriteDbRow
    this.albums = new ArrayList<>();
  }

  @Override
  public Fragment getItem(int position) {
    return null;
  }

  @Override
  public int getCount() {
    return 0;
  }

  enum OrderBy {
    TITLE, CREATION_DATE, MODIFY_DATE
  }

  enum OrderDirection {
    ASC, DESC
  }
}
