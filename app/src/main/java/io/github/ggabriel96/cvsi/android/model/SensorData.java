package io.github.ggabriel96.cvsi.android.model;

import android.hardware.SensorManager;
import android.support.annotation.NonNull;

/**
 * Created by gbrl on 04/09/17.
 */

public class SensorData implements Comparable<SensorData> {
  public Long timestamp;
  public float[] values;
  public Integer status;

  public float[] getOrientationValues() {
    float[] rotationMatrix = new float[9], orientationValues = new float[3];
    SensorManager.getRotationMatrixFromVector(rotationMatrix, this.values);
    SensorManager.getOrientation(rotationMatrix, orientationValues);
    return orientationValues;
  }

  @Override
  public int compareTo(@NonNull SensorData sd) {
    return this.timestamp.compareTo(sd.timestamp);
  }

}
