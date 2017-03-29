package io.github.ggabriel96.cvsi.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

@Entity
public class User {

  @Id
  private String uid;
  private String firstName;
  private String lastName;
  private String email;
  private String username;
  private Date birthday;
  private Date created;
  private Date signedIn;

  public User() {
  }

  public User(String uid, String firstName, String lastName, String email, String username, Date birthday, Date created, Date signedIn) {
    this.uid = uid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.username = username;
    this.birthday = birthday;
    this.created = created;
    this.signedIn = signedIn;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getSignedIn() {
    return signedIn;
  }

  public void setSignedIn(Date signedIn) {
    this.signedIn = signedIn;
  }
}
