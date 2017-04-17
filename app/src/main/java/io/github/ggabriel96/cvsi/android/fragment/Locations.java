package io.github.ggabriel96.cvsi.android.fragment;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;

import java.util.ArrayList;
import java.util.List;

import io.github.ggabriel96.cvsi.android.R;
import io.github.ggabriel96.cvsi.android.background.LocationHandler;

public class Locations extends Fragment implements LocationListener {

  private static final String TAG = "Locations";


  private List<Location> locations;
  private LocationHandler locationHandler;
  private ArrayAdapter<Location> arrayAdapter;

  public Locations() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate");
    super.onCreate(savedInstanceState);
    this.locations = new ArrayList<>();
    this.locationHandler = new LocationHandler(this.getContext(), this);
    this.arrayAdapter = new ArrayAdapter<Location>(this.getContext(), R.layout.simple_text, this.locations) {
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setText(Locations.this.locationHandler.locationToString(this.getItem(position)));
        return view;
      }

    };
    this.locationHandler.build();
  }

  @Override
  public void onStart() {
    Log.d(TAG, "onStart");
    super.onStart();
    this.locationHandler.connect(Boolean.TRUE);
  }

  @Override
  public void onResume() {
    Log.d(TAG, "onResume");
    super.onResume();
    this.locationHandler.startLocationUpdates();
  }

  @Override
  public void onPause() {
    Log.d(TAG, "onPause");
    super.onPause();
    this.locationHandler.stopLocationUpdates();
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "onDestroy");
    super.onDestroy();
    this.locationHandler.disconnect();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.d(TAG, "onCreateView");
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_locations, container, false);
    ListView listView = (ListView) v.findViewById(R.id.location_list);
    listView.setAdapter(this.arrayAdapter);
    return v;
  }

  @Override
  public void onLocationChanged(Location location) {
    Log.d(TAG, "onLocationChanged");
    Log.d(TAG, this.locationHandler.locationToString(location));
    this.locations.add(location);
    this.arrayAdapter.notifyDataSetChanged();
  }
}
