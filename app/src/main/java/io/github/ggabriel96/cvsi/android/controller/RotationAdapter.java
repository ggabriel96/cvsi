package io.github.ggabriel96.cvsi.android.controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.location.Location;

import com.google.android.gms.location.LocationListener;

import java.util.Arrays;

import io.github.ggabriel96.cvsi.android.model.SensorData;
import io.github.ggabriel96.cvsi.android.util.LocationGeotimeComparator;

/**
 * Created by gbrl on 15/09/17.
 */

public class RotationAdapter implements LocationListener {
  public static final int MAXSIZE = 1024;
  private static final String TAG = "RA";
  private SensorData[] rotationData;
  private SensorData[] accelerometerData;
  private SensorData[] gyroscopeData;
  private int rotationIndex, accelerometerIndex, gyroscopeIndex, locationIndex;
  private Location[] locations;


  public RotationAdapter() {
    this.rotationData = new SensorData[MAXSIZE];
    this.rotationIndex = 0;
    this.accelerometerData = new SensorData[MAXSIZE];
    this.accelerometerIndex = 0;
    this.gyroscopeData = new SensorData[MAXSIZE];
    this.gyroscopeIndex = 0;
    this.locations = new Location[MAXSIZE];
    this.locationIndex = 0;
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

  public SensorData[] getRotationData() {
    return rotationData;
  }

  public SensorData[] getAccelerometerData() {
    return accelerometerData;
  }

  public SensorData[] getGyroscopeData() {
    return gyroscopeData;
  }

  @Override
  public void onLocationChanged(Location location) {
    if (locationIndex == MAXSIZE) locationIndex = 0;
    this.locations[locationIndex] = location;
    locationIndex++;
  }

  public SensorData findClosestSensorData(Long timestamp, int sensorType) {
    switch (sensorType) {
      case Sensor.TYPE_ACCELEROMETER:
        return this.findClosestSensorData(this.accelerometerData, this.accelerometerIndex, timestamp);
      case Sensor.TYPE_GYROSCOPE:
        return this.findClosestSensorData(this.gyroscopeData, this.gyroscopeIndex, timestamp);
      case Sensor.TYPE_ROTATION_VECTOR:
        return this.findClosestSensorData(this.rotationData, this.rotationIndex, timestamp);
      default:
        return null;
    }
  }

  private SensorData findClosestSensorData(SensorData[] sensorDatas, Integer lastestIndex, Long timestamp) {
    int index = searchTimestamp(sensorDatas, lastestIndex, timestamp);
    if (index < 0) {
      index = -(index + 2);
      Long timeDelta = Math.abs(sensorDatas[index].timestamp - timestamp);
      Long previousTimeDelta = Math.abs(sensorDatas[index - 1].timestamp - timestamp);
      return sensorDatas[(timeDelta <= previousTimeDelta) ? index : index - 1];
    }
    return sensorDatas[index];
  }

  private int searchTimestamp(SensorData[] sensorDatas, Integer lastIndex, Long timestamp) {
    SensorData sensorData = new SensorData();
    sensorData.timestamp = timestamp;
    if (lastIndex + 1 < RotationAdapter.MAXSIZE && sensorDatas[lastIndex + 1] != null && timestamp <= sensorDatas[lastIndex].timestamp) {
      return Arrays.binarySearch(sensorDatas, lastIndex + 1, RotationAdapter.MAXSIZE, sensorData);
    } else {
      return Arrays.binarySearch(sensorDatas, 0, lastIndex + 1, sensorData);
    }
  }

  public Location findClosestLocation(Long timestamp) {
    int index = searchTimestamp(this.locations, this.locationIndex, timestamp);
    if (index < 0) {
      index = -(index + 2);
      Long timeDelta = Math.abs(this.locations[index].getTime() - timestamp);
      Long previousTimeDelta = Math.abs(this.locations[index - 1].getTime() - timestamp);
      return this.locations[(timeDelta <= previousTimeDelta) ? index : index - 1];
    }
    return this.locations[index];
  }

  private int searchTimestamp(Location[] locations, Integer lastIndex, Long timestamp) {
    Location location = new Location("manual");
    location.setTime(timestamp);
    if (lastIndex + 1 < RotationAdapter.MAXSIZE && locations[lastIndex + 1] != null && timestamp <= locations[lastIndex].getTime()) {
      return Arrays.binarySearch(locations, lastIndex + 1, RotationAdapter.MAXSIZE, location, new LocationGeotimeComparator());
    } else {
      return Arrays.binarySearch(locations, 0, lastIndex + 1, location, new LocationGeotimeComparator());
    }
  }
}