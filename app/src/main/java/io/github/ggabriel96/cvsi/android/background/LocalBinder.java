package io.github.ggabriel96.cvsi.android.background;

import android.os.Binder;

/**
 * Class used for the client Binder.  Because we know this service always
 * runs in the same process as its clients, we don't need to deal with IPC.
 */
public class LocalBinder extends Binder {
  private RotationService rotationService;

  public LocalBinder(RotationService rotationService) {
    this.rotationService = rotationService;
  }

  public RotationService getService() {
    return this.rotationService;


  }
}
