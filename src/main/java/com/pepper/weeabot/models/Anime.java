package com.pepper.weeabot.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Anime {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  private String title;
  private Float rating = 0F;
  private int ratingCount; 

  public Anime() {}

  public Anime(String title, Float rating) {
    this.title = title;
    this.updateRating(rating.intValue());
  }

  public Anime(String title) {
    this(title, 0F);
  }

  private Float weightedAverageByCount(int x) {
    return ((this.rating * (this.ratingCount - 1)) + x) / this.ratingCount;
  }

  @Override
  public String toString() {
    return String.format("Anime{id=%d, title='%s', rating=%f, ratingCount=%d}", id, title, rating, ratingCount);
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

  public int getRatingCount() {
    return ratingCount;
  }

  public Float getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = (float)rating;
  }

  public void updateRating(int newRating) {
    if (newRating > 0 && newRating <= 5) {
      this.ratingCount++;
      this.rating = weightedAverageByCount(newRating);
    }
  }

  public String getTitleAndRating() {
    return String.format("%s %f", getTitle(), getRating());
  }
}