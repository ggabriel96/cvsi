package io.github.ggabriel96.cvsi.android.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Simple BroadcastReceiver that intercepts connectivity changes.
 * From https://developer.android.com/training/basics/network-ops/managing.html
 */
public class NetworkListener extends BroadcastReceiver {

  private Boolean registered;
  private NetworkInfo networkInfo;

  public NetworkListener() {
    this.registered = Boolean.FALSE;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    this.networkInfo = connectivityManager.getActiveNetworkInfo();
  }

  /**
   * Register receiver for connectivity changes.
   *
   * @param context Context in which to register this receiver.
   * @return {@code null} if was already registered, else see Context.registerReceiver(BroadcastReceiver,IntentFilter).
   */
  public Intent register(Context context) {
    if (!this.registered) {
      this.registered = Boolean.TRUE;
      IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
      return context.registerReceiver(this, filter);
    }
    return null;
  }

  /**
   * Unregister receiver for previously registered connectivity changes.
   *
   * @param context Context in which to register this receiver.
   * @return {@code true} if was registered, {@code false} otherwise.
   */
  public Boolean unregister(Context context) {
    if (this.registered) {
      context.unregisterReceiver(this);
      this.registered = Boolean.FALSE;
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  /**
   * @return {@code true} if there is any network connectivity available, {@code false} otherwise.
   */
  public Boolean isOnline() {
    return (this.networkInfo != null && this.networkInfo.isConnected());
  }
}
