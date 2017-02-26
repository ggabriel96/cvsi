/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package io.github.ggabriel96.cvsi.backend;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Endpoint extends HttpServlet {

  private static final Logger LOG = Logger.getLogger("io.github.ggabriel96.cvsi.backend.Endpoint");

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
    Storage storage = StorageOptions.getDefaultInstance().getService();
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
      .setCredential(FirebaseCredentials.applicationDefault())
      .setDatabaseUrl("https://cvsi-backend.firebaseio.com/")
      .build();


    try {
      FirebaseApp.initializeApp(firebaseOptions);
    } catch (Exception error) {
      Endpoint.LOG.info("Already exists...");
    }

    Query<Entity> pictureQuery = Query.newEntityQueryBuilder().setKind("Picture")
      .setFilter(StructuredQuery.PropertyFilter.eq("id", 1)).build();
    QueryResults<Entity> queryResults = datastore.run(pictureQuery);
    Entity entity = queryResults.next();
    LOG.info(entity.getString("path"));
    Blob image = storage.get("cvsi-backend.appspot.com", entity.getString("path"));

    resp.setContentType(image.getContentType());
    resp.getOutputStream().write(image.getContent());
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
    String name = req.getParameter("name");
    resp.setContentType("text/plain");
    if (name != null) {
      resp.getWriter().println("Hello, " + name + "!");
    } else {
      resp.getWriter().println("Hello there!");
    }
  }
}
