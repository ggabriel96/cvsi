package io.github.ggabriel96.cvsi.android.background;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import io.github.ggabriel96.cvsi.backend.myApi.MyApi;

public class Endpoint extends AsyncTask<String, Void, String> {

  private static final String TAG = "Endpoint";

  private MyApi api;
  private Context context;

  /**
   * Creates a new asynchronous task. This constructor must be invoked on the UI thread.
   */
  public Endpoint(Context context) {
    super();
    Log.d(TAG, "Endpoint");
    this.buildApi();
    this.context = context;
  }

  /**
   * Sample code from https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
   */
  private void buildApi() {
    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
      // options for running against local devappserver
      // - 10.0.2.2 is localhost's IP address in Android emulator
      // - turn off compression when running against local devappserver
      .setApplicationName("cvsi-backend")
//      .setRootUrl("http://192.168.15.5:8080/_ah/api/")
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

  /**
   * Override this method to perform a computation on a background thread. The
   * specified parameters are the parameters passed to {@link #execute}
   * by the caller of this task.
   * <p>
   * This method can call {@link #publishProgress} to publish updates
   * on the UI thread.
   *
   * @param params The parameters of the task.
   * @return A result, defined by the subclass of this task.
   * @see #onPreExecute()
   * @see #onPostExecute
   * @see #publishProgress
   */
  @Override
  protected String doInBackground(String... params) {
    Log.d(TAG, "doInBackground");
    try {
      return this.api.sayHi(params[0]).execute().getData();
    } catch (IOException e) {
      return e.getMessage();
    }
  }

  /**
   * <p>Runs on the UI thread after {@link #doInBackground}. The
   * specified result is the value returned by {@link #doInBackground}.</p>
   * <p>
   * <p>This method won't be invoked if the task was cancelled.</p>
   *
   * @param s The result of the operation computed by {@link #doInBackground}.
   * @see #onPreExecute
   * @see #doInBackground
   * @see #onCancelled(Object)
   */
  @Override
  protected void onPostExecute(String s) {
    Log.d(TAG, "onPostExecute");
    Toast.makeText(this.context, s, Toast.LENGTH_LONG).show();
  }
}
