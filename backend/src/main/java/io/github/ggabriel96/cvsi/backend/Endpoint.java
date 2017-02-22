/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package io.github.ggabriel96.cvsi.backend;

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
//    FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
//      .setCredential(FirebaseCredentials.applicationDefault())
//      .setDatabaseUrl("https://cvsi-backend.firebaseio.com/")
//      .build();
//
//
//    try {
//      FirebaseApp.initializeApp(firebaseOptions);
//    } catch (Exception error) {
//      Endpoint.LOG.info("Already exists...");
//    }
//
//    Storage storage = StorageOptions.getDefaultInstance().getService();
//    Blob image = storage.get("cvsi-backend.appspot.com", "images/EnknEWerHud3wy3xORvstSbQnt52/98974275");
//
//    resp.setContentType(image.getContentType());
//    resp.getOutputStream().write(image.getContent());
    String name = req.getParameter("name");
    resp.setContentType("text/plain");
    if (name != null) {
      resp.getWriter().println("Hello, " + name + "!");
    } else {
      resp.getWriter().println("Hello there!");
    }
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
