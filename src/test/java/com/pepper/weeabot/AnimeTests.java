package com.pepper.weeabot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pepper.weeabot.models.Anime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AnimeTests {
  
  @Test
  void canUpdateRating() {
    Anime anime = new Anime("Attack on Titan");

    anime.updateRating(2); 
    anime.updateRating(5);
    anime.updateRating(5);
    anime.updateRating(1);
    anime.updateRating(3);
    anime.updateRating(3);
    anime.updateRating(1);
    anime.updateRating(2);
    anime.updateRating(3);
    anime.updateRating(1);
    anime.updateRating(2);
    anime.updateRating(0);
    anime.updateRating(4);

    assertEquals(12, anime.getRatingCount());
    assertEquals(0, Float.compare(anime.getRating(), 2.6666667F));
  }
}