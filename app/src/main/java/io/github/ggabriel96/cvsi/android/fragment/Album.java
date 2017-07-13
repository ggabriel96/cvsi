package io.github.ggabriel96.cvsi.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import io.github.ggabriel96.cvsi.android.R;
import io.github.ggabriel96.cvsi.android.sql.SQLiteContract;


public class Album extends Fragment {
  private String title;
  private String cover;
  private String description;
  private Date creationDate;
  private Date modifyDate;

  public Album() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment Album.
   */
  public static Album newInstance(String title, String cover, String description, Date creationDate, Date modifyDate) {
    Album fragment = new Album();
    fragment.title = title;
    fragment.cover = cover;
    fragment.description = description;
    fragment.creationDate = creationDate;
    fragment.modifyDate = modifyDate;
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null) {
      this.title = savedInstanceState.getString(SQLiteContract.AlbumEntry.COLUMN_TITLE);
      this.cover = savedInstanceState.getString(SQLiteContract.AlbumEntry.COLUMN_COVER);
      this.description = savedInstanceState.getString(SQLiteContract.AlbumEntry.COLUMN_DESCRIPTION);
      this.creationDate = new Date(savedInstanceState.getLong(SQLiteContract.AlbumEntry.COLUMN_CREATION_DATE));
      this.modifyDate = new Date(savedInstanceState.getLong(SQLiteContract.AlbumEntry.COLUMN_MODIFY_DATE));
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(SQLiteContract.AlbumEntry.COLUMN_TITLE, this.title);
    outState.putString(SQLiteContract.AlbumEntry.COLUMN_COVER, this.cover);
    outState.putString(SQLiteContract.AlbumEntry.COLUMN_DESCRIPTION, this.description);
    outState.putLong(SQLiteContract.AlbumEntry.COLUMN_CREATION_DATE, this.creationDate.getTime());
    outState.putLong(SQLiteContract.AlbumEntry.COLUMN_MODIFY_DATE, this.modifyDate.getTime());
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_album, container, false);
    ((TextView) v.findViewById(R.id.album_title)).setText(this.title);
    ((TextView) v.findViewById(R.id.album_description)).setText(this.description);
    return v;
  }
}
