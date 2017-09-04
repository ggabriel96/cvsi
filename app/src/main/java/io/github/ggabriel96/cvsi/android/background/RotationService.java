package io.github.ggabriel96.cvsi.android.background;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import io.github.ggabriel96.cvsi.android.model.RotationData;

/**
 * Created by gbrl on 04/09/17.
 */

public class RotationService extends Service implements SensorEventListener {
  private static final String TAG = "RS";
  private LocalBinder localBinder;
  private Sensor rotation;
  private RotationData rotationData;
  private SensorManager sensorManager;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    if (this.rotation != null) {
      Log.d(TAG, "registerSensors: Rotation vector");
      this.sensorManager.registerListener(this, this.rotation, SensorManager.SENSOR_DELAY_GAME);
    } else {
      Log.d(TAG, "registerSensors: Rotation vector not available!");
    }
    return this.localBinder;
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    if (this.rotation != null && event.sensor.equals(this.rotation)) {
      this.rotationData.timestamp = System.currentTimeMillis();
      this.rotationData.rotationValues = event.values;
    }
//    Log.d(TAG, "rotation: " + Arrays.lastLocationToString(this.rotationValues));
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    if (sensor.equals(this.rotation)) {
      this.rotationData.rotationStatus = accuracy;
      Log.d(TAG, "rotation accuracy: " + Integer.toString(this.rotationData.rotationStatus));
    }
  }

  @Override
  public void onCreate() {
    super.onCreate();
    this.rotationData = new RotationData();
    this.localBinder = new LocalBinder(this);
    this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    this.rotation = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    this.stopListener();
  }

  public RotationData getRotationData() {
    return this.rotationData;
  }

  public void stopListener() {
    this.sensorManager.unregisterListener(this);
    this.sensorManager = null;
  }
}
