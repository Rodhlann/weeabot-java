package com.pepper.weeabot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.pepper.weeabot.models.Anime;
import com.pepper.weeabot.utils.SlackRequestMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javassist.tools.web.BadHttpRequest;

@ExtendWith(MockitoExtension.class)
public class SlackRequestMapperTests {

  @Test
  void testParseListRequest() {
    List<Anime> animeList = new ArrayList<Anime>(
      List.of(
        new Anime("Fullmetal Alchemist"), 
        new Anime("One Punch Man")
      )
    );

    String expected = "1. Fullmetal Alchemist\n2. One Punch Man";
    String result = SlackRequestMapper.parseListRequest(animeList);

    assertTrue(expected.equals(result));
  }

  @Test
  void testParseListRequest_EmptyList() {
    List<Anime> animeList = new ArrayList<Anime>();

    String result = SlackRequestMapper.parseListRequest(animeList);

    assertTrue(SlackRequestMapper.emptyListResponse.equals(result));
  }

  @Test
  void testParseAddRequest() throws BadHttpRequest {
    final String title = "Fullmetal Alchemist";
    final float rating = 5;

    final String requestString = String.format("%s %f", title, rating);
    List<String> requestArgs = new ArrayList<String>(List.of(requestString.split("\\s+")));

    final Anime result = SlackRequestMapper.parseAddRequest(requestArgs);
    assertEquals(title, result.getTitle());
    assertEquals(rating, (float) result.getRating());
    assertEquals(1, result.getRatingCount());
  }

  @Test
  void testParseAddRequest_NameOnly() throws BadHttpRequest {
    final String title = "Fullmetal Alchemist";

    final String requestString = String.format("%s", title);
    List<String> requestArgs = new ArrayList<String>(List.of(requestString.split("\\s+")));

    final Anime result = SlackRequestMapper.parseAddRequest(requestArgs);
    assertEquals(title, result.getTitle());
    assertEquals(0F, (float) result.getRating());
    assertEquals(0, result.getRatingCount());
  }

  @Test
  void testParseAddRequest_NoName() throws Exception {
    final String title = "";

    final String requestString = String.format("%s", title);
    List<String> requestArgs = new ArrayList<String>(List.of(requestString.split("\\s+")));

    assertThrows(BadHttpRequest.class, () -> SlackRequestMapper.parseAddRequest(requestArgs));
  }
}