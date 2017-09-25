package io.github.ggabriel96.cvsi.backend.entity;

import com.google.type.LatLng;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

@Entity
public class Picture {

  @Id
  private Long id;
  private String title;
  private String description;
  private Date captured;
  private Float accelerometerX;
  private Float accelerometerY;
  private Float accelerometerZ;
  private Float gyroscopeX;
  private Float gyroscopeY;
  private Float gyroscopeZ;
  private Float rotationX;
  private Float rotationY;
  private Float rotationZ;
  private Float rotationCosine;
  private Float rotationAccuracy;
  @Index
  private LatLng location;
  private Float locationAccuracy;
  private Float locationBearing;
  private String locationProvider;
  private Date locationTime;
  private Ref<User> user;

  public Picture() {
  }

  public Picture(String title, String description, Date captured, User user) {
    this.title = title;
    this.description = description;
    this.captured = captured;
    this.user = Ref.create(user);
  }

  public User getUser() {
    return this.user.get();
  }

  public void setUser(User user) {
    this.user = Ref.create(user);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Float getAccelerometerX() {
    return accelerometerX;
  }

  public void setAccelerometerX(Float accelerometerX) {
    this.accelerometerX = accelerometerX;
  }

  public Float getAccelerometerY() {
    return accelerometerY;
  }

  public void setAccelerometerY(Float accelerometerY) {
    this.accelerometerY = accelerometerY;
  }

  public Float getAccelerometerZ() {
    return accelerometerZ;
  }

  public void setAccelerometerZ(Float accelerometerZ) {
    this.accelerometerZ = accelerometerZ;
  }

  public Float getGyroscopeX() {
    return gyroscopeX;
  }

  public void setGyroscopeX(Float gyroscopeX) {
    this.gyroscopeX = gyroscopeX;
  }

  public Float getGyroscopeY() {
    return gyroscopeY;
  }

  public void setGyroscopeY(Float gyroscopeY) {
    this.gyroscopeY = gyroscopeY;
  }

  public Float getGyroscopeZ() {
    return gyroscopeZ;
  }

  public void setGyroscopeZ(Float gyroscopeZ) {
    this.gyroscopeZ = gyroscopeZ;
  }

  public Float getRotationX() {
    return rotationX;
  }

  public void setRotationX(Float rotationX) {
    this.rotationX = rotationX;
  }

  public Float getRotationY() {
    return rotationY;
  }

  public void setRotationY(Float rotationY) {
    this.rotationY = rotationY;
  }

  public Float getRotationZ() {
    return rotationZ;
  }

  public void setRotationZ(Float rotationZ) {
    this.rotationZ = rotationZ;
  }

  public Float getRotationCosine() {
    return rotationCosine;
  }

  public void setRotationCosine(Float rotationCosine) {
    this.rotationCosine = rotationCosine;
  }

  public Float getRotationAccuracy() {
    return rotationAccuracy;
  }

  public void setRotationAccuracy(Float rotationAccuracy) {
    this.rotationAccuracy = rotationAccuracy;
  }

  public Float getLocationAccuracy() {
    return locationAccuracy;
  }

  public void setLocationAccuracy(Float locationAccuracy) {
    this.locationAccuracy = locationAccuracy;
  }

  public Float getLocationBearing() {
    return locationBearing;
  }

  public void setLocationBearing(Float locationBearing) {
    this.locationBearing = locationBearing;
  }

  public String getLocationProvider() {
    return locationProvider;
  }

  public void setLocationProvider(String locationProvider) {
    this.locationProvider = locationProvider;
  }

  public Date getLocationTime() {
    return locationTime;
  }

  public void setLocationTime(Date locationTime) {
    this.locationTime = locationTime;
  }

  public LatLng getLocation() {
    return location;
  }

  public void setLocation(LatLng location) {
    this.location = location;
  }
}
