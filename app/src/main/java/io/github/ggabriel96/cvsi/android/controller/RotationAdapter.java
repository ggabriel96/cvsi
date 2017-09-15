package io.github.ggabriel96.cvsi.android.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

import io.github.ggabriel96.cvsi.android.model.RotationData;

/**
 * Created by gbrl on 15/09/17.
 */

public class RotationAdapter {
  private static final String TAG = "RA";
  private static final int MAXSIZE = 1024;
  private RotationData[] rotationDatas;
  private int index;

  public RotationAdapter() {
    this.rotationDatas = new RotationData[MAXSIZE];
    this.index = 0;
  }

  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    if (index == MAXSIZE) index = 0;
    this.rotationDatas[index].rotationStatus = accuracy;
    Log.d(TAG, "rotation accuracy: " + Integer.toString(this.rotationDatas[index].rotationStatus));
  }

  public void onSensorChanged(SensorEvent event) {
    this.rotationDatas[index].timestamp = System.currentTimeMillis();
    this.rotationDatas[index].rotationValues = event.values;
    index++;
  }

}
