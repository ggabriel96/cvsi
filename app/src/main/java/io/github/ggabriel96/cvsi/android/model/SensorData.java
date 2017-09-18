package io.github.ggabriel96.cvsi.android.model;

import android.hardware.SensorManager;

/**
 * Created by gbrl on 04/09/17.
 */

public class SensorData {
  public Long timestamp;
  public float[] values;
  public Integer status;

  public float[] getOrientationValues() {
    float[] rotationMatrix = new float[9], orientationValues = new float[3];
    SensorManager.getRotationMatrixFromVector(rotationMatrix, this.values);
    SensorManager.getOrientation(rotationMatrix, orientationValues);
    //Log.d(TAG, "orientation values: " + Arrays.toString(orientationValues));
    return orientationValues;
  }
}
