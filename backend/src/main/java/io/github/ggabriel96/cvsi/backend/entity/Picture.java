package io.github.ggabriel96.cvsi.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Picture {

  @Id
  @Index
  private Long id;

  @Index
  private String path;

  public Picture() {
  }

  public Picture(Long id, String path) {
    this.id = id;
    this.path = path;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
