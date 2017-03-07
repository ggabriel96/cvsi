/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package io.github.ggabriel96.cvsi.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 * <p>
 * Generate openapi.json:
 * .\endpoints-framework-tools-2.0.4\bin\endpoints-framework-tools.bat get-openapi-doc --hostname=cvsi-backend.appspot.com --war=.\backend\build\exploded-app io.github.ggabriel96.cvsi.backend.MyEndpoint
 * Then publish:
 * gcloud service-management deploy openapi.json
 */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.cvsi.ggabriel96.github.io",
    ownerName = "backend.cvsi.ggabriel96.github.io",
    packagePath = ""
  )
)
public class MyEndpoint {

  /**
   * A simple endpoint method that takes a name and says Hi back
   */
  @ApiMethod(name = "sayHi")
  public MyBean sayHi(@Named("name") String name) {
    Logger.getLogger(this.getClass().getName()).info("sayHi");
    MyBean response = new MyBean();
    response.setData("Hi, " + name);
    return response;
  }

}
