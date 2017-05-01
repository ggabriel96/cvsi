package io.github.ggabriel96.cvsi.android.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import io.github.ggabriel96.cvsi.android.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

  private GoogleMap mMap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }


  /**
   * Manipulates the map once available.
   * This callback is triggered when the map is ready to be used.
   * This is where we can add markers or lines, add listeners or move the camera. In this case,
   * we just add a marker near Sydney, Australia.
   * If Google Play services is not installed on the device, the user will be prompted to install
   * it inside the SupportMapFragment. This method will only be triggered once the user has
   * installed Google Play services and returned to the app.
   */
  @Override
  public void onMapReady(GoogleMap googleMap) throws SecurityException {
    mMap = googleMap;
    mMap.setMyLocationEnabled(true);
    mMap.setOnMarkerClickListener(this);
    mMap.setOnMapLongClickListener(this);
  }

  @Override
  public void onMapLongClick(LatLng latLng) {
    mMap.addMarker(new MarkerOptions().position(latLng).title("Marked location"));
    Toast.makeText(this, "(" + latLng.latitude + ", " + latLng.longitude + ")", Toast.LENGTH_SHORT).show();
  }

  @Override
  public boolean onMarkerClick(Marker marker) {
    if (marker.isVisible()) {
      marker.remove();
      return true;
    }
    return false;
  }
}
