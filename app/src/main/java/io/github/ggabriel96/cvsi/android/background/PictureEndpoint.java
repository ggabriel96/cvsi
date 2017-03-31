package io.github.ggabriel96.cvsi.android.background;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import io.github.ggabriel96.cvsi.backend.myApi.model.Picture;

public class PictureEndpoint extends EndpointTask<Picture, Picture, Void> {

  private static final String TAG = "PictureEndpoint";

  public PictureEndpoint(Context context, final String authToken) {
    super(context, authToken);
  }

  @Override
  protected void onPreExecute() {
    Toast.makeText(this.context, "Saving picture to Datastore...", Toast.LENGTH_SHORT).show();
  }

  @Override
  protected Void doInBackground(Picture... params) {
    for (Picture picture : params) {
      try {
        this.publishProgress(this.api.insert(picture).execute());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @Override
  protected void onProgressUpdate(Picture... values) {
    Log.d(TAG, "onProgressUpdate");
    if (values != null && values.length > 0) {
      String message = "Picture " + values[0].getTitle() + " uploaded successfully with ID " + values[0].getId();
      Log.d(TAG, message);
      Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show();
    }
  }
}
