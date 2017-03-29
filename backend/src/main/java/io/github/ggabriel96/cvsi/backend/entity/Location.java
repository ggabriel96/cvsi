package io.github.ggabriel96.cvsi.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Location {

  @Id
  private Long id;
  @Index
  private Float accelerometerX;
  @Index
  private Float accelerometerY;
  @Index
  private Float accelerometerZ;
  @Index
  private Float gyroscopeX;
  @Index
  private Float gyroscopeY;
  @Index
  private Float gyroscopeZ;
  @Index
  private Float rotationX;
  @Index
  private Float rotationY;
  @Index
  private Float rotationZ;
  @Index
  private Float rotationCosine;
  @Index
  private Float rotationAccuracy;
  @Index
  private Double latitude;
  @Index
  private Double longitude;
  @Index
  private Double altitude;
  @Index
  private Float locationAccuracy;
  @Index
  private Float locationBearing;
  @Index
  private String locationProvider;
  @Index
  private Long locationTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getAltitude() {
    return altitude;
  }

  public void setAltitude(Double altitude) {
    this.altitude = altitude;
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

  public Long getLocationTime() {
    return locationTime;
  }

  public void setLocationTime(Long locationTime) {
    this.locationTime = locationTime;
  }
}
