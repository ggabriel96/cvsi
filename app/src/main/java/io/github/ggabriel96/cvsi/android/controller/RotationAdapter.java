package io.github.ggabriel96.cvsi.android.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import io.github.ggabriel96.cvsi.android.model.SensorData;

/**
 * Created by gbrl on 15/09/17.
 */

public class RotationAdapter {
  private static final String TAG = "RA";
  private static final int MAXSIZE = 1024;
  private SensorData[] rotationData, accelerometerData, gyroscopeData;
  private int rotationIndex, accelerometerIndex, gyroscopeIndex;


  public RotationAdapter() {
    this.rotationData = new SensorData[MAXSIZE];
    this.rotationIndex = 0;
    this.accelerometerData = new SensorData[MAXSIZE];
    this.accelerometerIndex = 0;
    this.gyroscopeData = new SensorData[MAXSIZE];
    this.gyroscopeIndex = 0;
  }

  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    switch (sensor.getType()) {
      case Sensor.TYPE_ACCELEROMETER:
        this.accelerometerData[accelerometerIndex].status = accuracy;
        break;
      case Sensor.TYPE_GYROSCOPE:
        this.gyroscopeData[gyroscopeIndex].status = accuracy;
        break;
      case Sensor.TYPE_ROTATION_VECTOR:
        this.rotationData[rotationIndex].status = accuracy;
        break;
    }
  }

  public void onSensorChanged(SensorEvent event) {

    switch (event.sensor.getType()) {
      case Sensor.TYPE_ACCELEROMETER:
        if (accelerometerIndex == MAXSIZE) accelerometerIndex = 0;
        this.accelerometerData[accelerometerIndex].timestamp = System.currentTimeMillis();
        this.accelerometerData[accelerometerIndex].values = event.values;
        accelerometerIndex++;
        break;
      case Sensor.TYPE_GYROSCOPE:
        if (gyroscopeIndex == MAXSIZE) gyroscopeIndex = 0;
        this.gyroscopeData[gyroscopeIndex].timestamp = System.currentTimeMillis();
        this.gyroscopeData[gyroscopeIndex].values = event.values;
        gyroscopeIndex++;
        break;
      case Sensor.TYPE_ROTATION_VECTOR:
        if (rotationIndex == MAXSIZE) rotationIndex = 0;
        this.rotationData[rotationIndex].timestamp = System.currentTimeMillis();
        this.rotationData[rotationIndex].values = event.values;
        rotationIndex++;
        break;
    }
  }

}
