package com.yummynoodlebar.persistence.domain;

import org.springframework.data.mongodb.core.index.Indexed;

public class Ingredient {
  @Indexed
  private String name;
  private String description;

  public Ingredient(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
