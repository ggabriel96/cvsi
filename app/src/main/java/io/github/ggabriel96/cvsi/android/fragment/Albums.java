package io.github.ggabriel96.cvsi.android.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.github.ggabriel96.cvsi.android.R;
import io.github.ggabriel96.cvsi.android.interfaces.DialogListener;
import io.github.ggabriel96.cvsi.android.interfaces.SQLiteListener;
import io.github.ggabriel96.cvsi.android.sql.SQLiteContract;
import io.github.ggabriel96.cvsi.android.sql.SQLiteHelper;

public class Albums extends Fragment implements DialogListener {

  private static final String TAG = "Albums";
  private FragmentManager fragmentManager;
  private AlbumPagerAdapter albumPagerAdapter;
  private ViewPager viewPager;
  private SQLiteListener sqLiteListener;

  private SQLiteHelper sqLiteHelper;
  private SQLiteDatabase sqLiteDatabase;


  public Albums() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setHasOptionsMenu(true);
    this.fragmentManager = this.getChildFragmentManager();
    this.sqLiteHelper = new SQLiteHelper(this.getContext());
    this.sqLiteDatabase = this.sqLiteHelper.getWritableDatabase();
    this.albumPagerAdapter = new AlbumPagerAdapter(this, this.fragmentManager, AlbumPagerAdapter.OrderBy.TITLE);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.albums_menu, menu);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View albumsView = inflater.inflate(R.layout.fragment_albums, container, false);
    this.viewPager = (ViewPager) albumsView.findViewById(R.id.albums_pager);
    this.viewPager.setAdapter(this.albumPagerAdapter);
    return albumsView;
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.album_create:
        NewAlbum newAlbum = new NewAlbum();
        newAlbum.setDialogListener(this);
        newAlbum.show(this.fragmentManager, "create_album");
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onDialogPositiveClick(DialogFragment dialog) {
    String albumTitle = ((TextView) dialog.getDialog().findViewById(R.id.album_title_new)).getText().toString();
    String albumDescription = ((TextView) dialog.getDialog().findViewById(R.id.album_description_new)).getText().toString();
    ContentValues cv = SQLiteContract.AlbumEntry.getInsertContentValues(albumTitle, albumDescription, new Date());
    this.sqLiteDatabase.insert(SQLiteContract.AlbumEntry.TABLE_NAME, null, cv);
    this.sqLiteListener.onInsert(cv);

  }

  @Override
  public void onDialogNegativeClick(DialogFragment dialog) {
  }

  protected List<Album> getAlbumsFromDB(AlbumPagerAdapter.OrderBy orderBy, AlbumPagerAdapter.OrderDirection orderDirection) {

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

    if (orderBy == AlbumPagerAdapter.OrderBy.CREATION_DATE)
      sortOrder = SQLiteContract.AlbumEntry.COLUMN_CREATION_DATE;
    else if (orderBy == AlbumPagerAdapter.OrderBy.MODIFY_DATE)
      sortOrder = SQLiteContract.AlbumEntry.COLUMN_MODIFY_DATE;
    else sortOrder = SQLiteContract.AlbumEntry.COLUMN_TITLE;

    if (orderDirection == AlbumPagerAdapter.OrderDirection.DESC) sortOrder += " DESC";
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

  public SQLiteListener getSqLiteListener() {
    return this.sqLiteListener;
  }

  public void setSqLiteListener(SQLiteListener sqLiteListener) {
    this.sqLiteListener = sqLiteListener;
  }
}
