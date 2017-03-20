package io.github.ggabriel96.cvsi.android.camera;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

  private ShootingActivity shootingActivity;

  public GestureListener(ShootingActivity shootingActivity) {
    this.shootingActivity = shootingActivity;
  }

  @Override
  public boolean onSingleTapConfirmed(MotionEvent e) {
    Log.i("GESTURE", "onSingleTapConfirmed");
    this.shootingActivity.takePicture();
    return true;
  }
}
