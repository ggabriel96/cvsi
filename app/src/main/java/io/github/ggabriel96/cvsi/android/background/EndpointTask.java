package io.github.ggabriel96.cvsi.android.background;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import io.github.ggabriel96.cvsi.backend.myApi.MyApi;

public abstract class EndpointTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

  private static final String TAG = "EndpointTask";
  protected Context context;
  MyApi api;

  /**
   * Creates a new asynchronous task. This constructor must be invoked on the UI thread.
   */
  public EndpointTask(Context context, final String authToken) {
    super();
    Log.d(TAG, "Abstract constructor");
    this.buildApi(authToken);
    this.context = context;
  }

  /**
   * Sample code from https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
   */
  private void buildApi(@NonNull final String authToken) {
    AuthenticatedHttpRequest httpRequestInitializer = new AuthenticatedHttpRequest(authToken);
    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport()
      , new AndroidJsonFactory()
      , httpRequestInitializer)
      // options for running against local devappserver
      // - 10.0.2.2 is localhost's IP address in Android emulator
      // - turn off compression when running against local devappserver
      .setApplicationName("cvsi-backend")
//      .setRootUrl("http://192.168.15.9:8080/_ah/api/")
      .setRootUrl("https://cvsi-backend.appspot.com/_ah/api/")
      .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
        @Override
        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
          abstractGoogleClientRequest.setDisableGZipContent(true);
        }
      });
    // end options for devappserver

    this.api = builder.build();
  }

}
