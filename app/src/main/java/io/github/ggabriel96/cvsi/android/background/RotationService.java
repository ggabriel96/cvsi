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

import io.github.ggabriel96.cvsi.android.controller.RotationAdapter;

/**
 * Created by gbrl on 04/09/17.
 */

public class RotationService extends Service implements SensorEventListener {
  private static final String TAG = "RS";
  private LocalBinder localBinder;
  private Sensor accelerometer, gyroscope, rotation;
  private RotationAdapter rotationAdapter;
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

    if (this.accelerometer != null) {
      Log.d(TAG, "registerSensors: Accelerometer");
      this.sensorManager.registerListener(this, this.accelerometer, SensorManager.SENSOR_DELAY_GAME);
    } else {
      Log.d(TAG, "registerSensors: Accelerometer not available!");
    }

    if (gyroscope != null) {
      Log.d(TAG, "registerSensors: Gyroscope");
      this.sensorManager.registerListener(this, this.gyroscope, SensorManager.SENSOR_DELAY_GAME);
    } else {
      Log.d(TAG, "registerSensors: Gyroscope not available!");
    }

    return this.localBinder;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    this.rotationAdapter.onSensorChanged(event);
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    this.rotationAdapter.onAccuracyChanged(sensor, accuracy);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    this.rotationAdapter = new RotationAdapter();
    this.localBinder = new LocalBinder(this);
    this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    this.gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    this.rotation = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    this.stopListener();
  }

  public RotationAdapter getRotationAdapter() {
    return this.rotationAdapter;
  }

  public void stopListener() {
    this.sensorManager.unregisterListener(this);
    this.sensorManager = null;
  }
}
