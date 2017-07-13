package io.github.ggabriel96.cvsi.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.ggabriel96.cvsi.android.R;

public class Albums extends Fragment {

  private static final String TAG = "Albums";
  private FragmentManager fragmentManager;
  private AlbumPagerAdapter albumPagerAdapter;
  private ViewPager viewPager;

  public Albums() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setHasOptionsMenu(true);
    this.fragmentManager = this.getChildFragmentManager();
    this.albumPagerAdapter = new AlbumPagerAdapter(this.getContext(),this.fragmentManager, AlbumPagerAdapter.OrderBy.TITLE);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.albums_menu, menu);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View albumsView = inflater.inflate(R.layout.fragment_albums, container, false);
    this.viewPager = (ViewPager)albumsView.findViewById(R.id.albums_pager);
    this.viewPager.setAdapter(this.albumPagerAdapter);
    return albumsView;
  }

  @Override
  public void onStart() {
    super.onStart();
  }
}
