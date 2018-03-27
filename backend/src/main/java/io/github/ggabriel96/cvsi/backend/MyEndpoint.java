/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package io.github.ggabriel96.cvsi.backend;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

import io.github.ggabriel96.cvsi.backend.entity.Picture;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * An endpoint class we are exposing
 * <p>
 * Generate openapi.json:
 * ./endpoints-framework-tools-2.0.13/bin/endpoints-framework-tools get-openapi-doc --hostname=cvsi-backend.appspot.com --war=./backend/build/exploded-app io.github.ggabriel96.cvsi.backend.MyEndpoint
 * ./endpoints-framework-tools-2.0.13/bin/endpoints-framework-tools get-client-lib --war=./backend/build/exploded-app -bs gradle io.github.ggabriel96.cvsi.backend.MyEndpoint

 * Then publish:
 * gcloud service-management deploy openapi.json
 */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.cvsi.ggabriel96.github.io",
    ownerName = "backend.cvsi.ggabriel96.github.io"
  ),
  authenticators = {FirebaseAuthenticator.class}
)
public class MyEndpoint {

  private static final Logger logger = Logger.getLogger(MyEndpoint.class.getName());

  /**
   * A simple endpoint method that takes a name and says Hi back
   */
  @ApiMethod(name = "sayHi", httpMethod = ApiMethod.HttpMethod.POST)
  public MyBean sayHi(User user, @Named("name") String name) {
    MyBean response = new MyBean();
    if (user == null) {
      response.setData("Hi, anonymous '" + name + "'!");
    } else {
      response.setData("Hi, authenticated '" + name + "'.");
    }
    return response;
  }

  /**
   * Inserts a new {@link Picture}.
   */
  @ApiMethod(
    name = "insert",
    path = "picture",
    httpMethod = ApiMethod.HttpMethod.POST)
  public Picture insert(Picture picture) {
    // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
    // You should validate that picture.id has not been set. If the ID type is not supported by the
    // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
    //
    // If your client provides the ID then you should probably use PUT instead.
    ofy().save().entity(picture).now();
    logger.info("Created Picture with ID: " + picture.getId());
    return ofy().load().entity(picture).now();
  }
}
