package io.github.ggabriel96.cvsi.android.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by gbrl on 10/07/17.
 */

public class AlbumPagerAdapter extends FragmentPagerAdapter {

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


  enum OrderBy {
    TITLE, CREATION_DATE, MODIFY_DATE
  }

  enum OrderDirection {
    ASC, DESC
  }
}
