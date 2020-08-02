package com.pepper.weeabot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.pepper.weeabot.models.Anime;
import com.pepper.weeabot.models.SlackRequest;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class AnimeControllerTests {

  @MockBean
  AnimeRepository repository;
  @Autowired
  AnimeController subject;
  @Captor
  ArgumentCaptor<Anime> captor;

  @Test
  void testHandleSlackRequest() throws Exception {
    String title = "Fullmetal Alchemist";
    float rating = 5;

    String requestString = String.format("add %s %f", title, rating);
    SlackRequest request = new SlackRequest(null, null, null, null, null, null, null, null, requestString, null);

    subject.handleSlackRequest(request);

    // TODO: Figure out why this is being called twice
    verify(repository, times(2)).save(captor.capture());
    assertEquals(title, captor.getValue().getTitle());
    assertEquals(rating, captor.getValue().getRating());
    assertEquals(1, captor.getValue().getRatingCount());
  }
}