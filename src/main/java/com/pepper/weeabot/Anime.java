package com.pepper.weeabot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Anime {
  @Id
  @GeneratedValue
  private Long id;
  private String title;
  private int rating;

  public Anime() {}

  public Anime(String title, int rating) {
    this.title = title;
    this.rating = rating;
  }

  public Anime(String title) {
    this(title, 0);
  }

  @Override
  public String toString() {
    return String.format("Anime{id=%d, title='%s', rating=%d}", id, title, rating);
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

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating > 0 && rating < 5 ? rating : this.rating;
  }
}