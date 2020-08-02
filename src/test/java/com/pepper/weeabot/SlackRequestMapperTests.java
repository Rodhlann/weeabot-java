package com.pepper.weeabot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pepper.weeabot.models.Anime;
import com.pepper.weeabot.utils.SlackRequestMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SlackRequestMapperTests {
  
  @Test
  void testCanAddAnime() throws Exception {
    String title = "Fullmetal Alchemist";
    float rating = 5;

    String requestString = String.format("add %s %f", title, rating);

    Anime result = SlackRequestMapper.mapRequest(requestString);
    assertEquals(title, result.getTitle());
    assertEquals(rating, result.getRating());
    assertEquals(1, result.getRatingCount());
  }
}