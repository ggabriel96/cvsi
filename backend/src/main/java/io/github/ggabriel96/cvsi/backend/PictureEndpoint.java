package io.github.ggabriel96.cvsi.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import io.github.ggabriel96.cvsi.backend.entity.Picture;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Api(
  name = "pictureApi",
  version = "v1",
  resource = "picture",
  namespace = @ApiNamespace(
    ownerDomain = "backend.cvsi.ggabriel96.github.io",
    ownerName = "backend.cvsi.ggabriel96.github.io",
    packagePath = ""
  )
)
public class PictureEndpoint {

  private static final Logger logger = Logger.getLogger(PictureEndpoint.class.getName());

  private static final int DEFAULT_LIST_LIMIT = 20;

  static {
    // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
    ObjectifyService.register(Picture.class);
  }

  /**
   * Returns the {@link Picture} with the corresponding ID.
   *
   * @param id the ID of the entity to be retrieved
   * @return the entity with the corresponding ID
   * @throws NotFoundException if there is no {@code Picture} with the provided ID.
   */
  @ApiMethod(
    name = "get",
    path = "picture/{id}",
    httpMethod = ApiMethod.HttpMethod.GET)
  public Picture get(@Named("id") Long id) throws NotFoundException {
    logger.info("Getting Picture with ID: " + id);
    Picture picture = ofy().load().type(Picture.class).id(id).now();
    if (picture == null) {
      throw new NotFoundException("Could not find Picture with ID: " + id);
    }
    return picture;
  }

  /**
   * Inserts a new {@code Picture}.
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

  /**
   * Updates an existing {@code Picture}.
   *
   * @param id      the ID of the entity to be updated
   * @param picture the desired state of the entity
   * @return the updated version of the entity
   * @throws NotFoundException if the {@code id} does not correspond to an existing
   *                           {@code Picture}
   */
  @ApiMethod(
    name = "update",
    path = "picture/{id}",
    httpMethod = ApiMethod.HttpMethod.PUT)
  public Picture update(@Named("id") Long id, Picture picture) throws NotFoundException {
    // TODO: You should validate your ID parameter against your resource's ID here.
    checkExists(id);
    ofy().save().entity(picture).now();
    logger.info("Updated Picture: " + picture);
    return ofy().load().entity(picture).now();
  }

  /**
   * Deletes the specified {@code Picture}.
   *
   * @param id the ID of the entity to delete
   * @throws NotFoundException if the {@code id} does not correspond to an existing
   *                           {@code Picture}
   */
  @ApiMethod(
    name = "remove",
    path = "picture/{id}",
    httpMethod = ApiMethod.HttpMethod.DELETE)
  public void remove(@Named("id") Long id) throws NotFoundException {
    checkExists(id);
    ofy().delete().type(Picture.class).id(id).now();
    logger.info("Deleted Picture with ID: " + id);
  }

  /**
   * List all entities.
   *
   * @param cursor used for pagination to determine which page to return
   * @param limit  the maximum number of entries to return
   * @return a response that encapsulates the result list and the next page token/cursor
   */
  @ApiMethod(
    name = "list",
    path = "picture",
    httpMethod = ApiMethod.HttpMethod.GET)
  public CollectionResponse<Picture> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
    limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
    Query<Picture> query = ofy().load().type(Picture.class).limit(limit);
    if (cursor != null) {
      query = query.startAt(Cursor.fromWebSafeString(cursor));
    }
    QueryResultIterator<Picture> queryIterator = query.iterator();
    List<Picture> pictureList = new ArrayList<Picture>(limit);
    while (queryIterator.hasNext()) {
      pictureList.add(queryIterator.next());
    }
    return CollectionResponse.<Picture>builder().setItems(pictureList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
  }

  private void checkExists(Long id) throws NotFoundException {
    try {
      ofy().load().type(Picture.class).id(id).safe();
    } catch (com.googlecode.objectify.NotFoundException e) {
      throw new NotFoundException("Could not find Picture with ID: " + id);
    }
  }
}
