//package io.github.ggabriel96.cvsi.android.background;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.widget.Toast;
//
//import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.extensions.android.json.AndroidJsonFactory;
//import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
//import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
//
//import java.io.IOException;
//
//import io.github.ggabriel96.cvsi.backend.pictureApi.PictureApi;
//import io.github.ggabriel96.cvsi.backend.pictureApi.model.Picture;
//
//public class PictureEndpoint extends AsyncTask<Picture, Void, Void> {
//
//  private Context context;
//  private PictureApi pictureApi;
//
//  public PictureEndpoint() {
//    super();
//    this.buildApi();
//    this.context = context;
//  }
//
//  private void buildApi() {
//    PictureApi.Builder builder = new PictureApi.Builder(AndroidHttp.newCompatibleTransport()
//      , new AndroidJsonFactory()
//      , null)
//      .setApplicationName("cvsi-backend")
//      .setRootUrl("http://192.168.15.3:8080/_ah/api/")
////      .setRootUrl("https://cvsi-backend.appspot.com/_ah/api/")
//      .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//        @Override
//        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//          abstractGoogleClientRequest.setDisableGZipContent(true);
//        }
//      });
//    // end options for devappserver
//
//    this.pictureApi = builder.build();
//  }
//
//  @Override
//  protected Void doInBackground(Picture... params) {
//    for (Picture picture : params) {
//      try {
//        this.pictureApi.insert(picture).execute();
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//    return null;
//  }
//
//  @Override
//  protected void onPostExecute(Void aVoid) {
//    super.onPostExecute(aVoid);
//    Toast.makeText(this.context, "Picture metadata upload is done.", Toast.LENGTH_LONG).show();
//  }
//}
