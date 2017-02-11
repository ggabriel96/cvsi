/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package io.github.ggabriel96.cvsi.backend;

import java.io.IOException;

import javax.servlet.http.*;

public class Endpoint extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
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
