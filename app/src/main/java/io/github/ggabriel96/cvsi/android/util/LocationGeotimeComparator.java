package io.github.ggabriel96.cvsi.android.util;

import android.location.Location;

import java.util.Comparator;

/**
 * Created by gbrl on 25/09/17.
 */

public class LocationGeotimeComparator implements Comparator<Location> {


  @Override
  public int compare(Location locationX, Location locationY) {
    return (int) (locationX.getTime() - locationY.getTime());
  }
}
