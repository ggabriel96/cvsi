package io.github.ggabriel96.cvsi.backend.entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

import java.util.Date;

@Entity
public class Picture {

  @Id
  private Long id;
  private String title;
  private String description;
  private Date captured;
  @Load
  private Ref<Location> location;
  private Ref<User> user;

  public Picture() {
  }

  public Picture(String title, String description, Date captured, Location location, User user) {
    this.title = title;
    this.description = description;
    this.captured = captured;
    this.location = Ref.create(location);
    this.user = Ref.create(user);
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getCaptured() {
    return captured;
  }

  public void setCaptured(Date captured) {
    this.captured = captured;
  }

  public Location getLocation() {
    return this.location.get();
  }

  public void setLocation(Location location) {
    this.location = Ref.create(location);
  }

  public User getUser() {
    return this.user.get();
  }

  public void setUser(User user) {
    this.user = Ref.create(user);
  }
}
