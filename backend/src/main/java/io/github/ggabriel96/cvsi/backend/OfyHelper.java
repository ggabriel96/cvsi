package io.github.ggabriel96.cvsi.backend;

import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import io.github.ggabriel96.cvsi.backend.entity.Location;
import io.github.ggabriel96.cvsi.backend.entity.Picture;
import io.github.ggabriel96.cvsi.backend.entity.User;

public class OfyHelper implements ServletContextListener {

  public void contextInitialized(ServletContextEvent event) {
    // This will be invoked as part of a warmup request,
    // or the first user request if no warmup request.
    ObjectifyService.register(User.class);
    ObjectifyService.register(Picture.class);
    ObjectifyService.register(Location.class);
  }

  public void contextDestroyed(ServletContextEvent event) {
    // App Engine does not currently invoke this method.
  }

}
