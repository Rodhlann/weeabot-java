package com.pepper.weeabot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pepper.weeabot.models.Anime;
import com.pepper.weeabot.models.SlackRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javassist.tools.web.BadHttpRequest;

@ExtendWith(MockitoExtension.class)
class AnimeControllerTests {

  private static final String HttpEntity = null;
  @InjectMocks
  AnimeController subject;
  @Mock
  AnimeRepository repository;
  @Mock
  RestTemplate restTemplate;
  @Captor
  ArgumentCaptor<Anime> captor;

  @Test
  void testHandleSlackRequest_Add_FullEntity() throws BadHttpRequest {
    final String title = "Fullmetal Alchemist";
    final float rating = 5;

    final String requestString = String.format("add %s %f", title, rating);
    final SlackRequest request = new SlackRequest(null, null, null, null, null, null, null, null, requestString, null);

    subject.handleSlackRequest(request);

    // TODO: Figure out why this is being called twice
    verify(repository, times(2)).save(captor.capture());
    assertEquals(title, captor.getValue().getTitle());
    assertEquals(rating, (float)captor.getValue().getRating());
    assertEquals(1, captor.getValue().getRatingCount());
  }

  @Test
  void testHandleSlackRequest_Add_NameOnly() throws BadHttpRequest {
    final String title = "Fullmetal Alchemist";

    final String requestString = String.format("add %s", title);
    final SlackRequest request = new SlackRequest(null, null, null, null, null, null, null, null, requestString, null);

    subject.handleSlackRequest(request);

    verify(repository, times(1)).save(captor.capture());
    assertEquals(title, captor.getValue().getTitle());
    assertEquals(0, (float)captor.getValue().getRating());
    assertEquals(0, captor.getValue().getRatingCount());
  }

  @Test
  void testHandleSlackRequest_Delete() throws BadHttpRequest {
    final String title = "Fullmetal Alchemist";

    final String requestString = String.format("delete %s", title);
    final SlackRequest request = new SlackRequest(null, null, null, null, null, null, null, null, requestString, null);

    Optional<Anime> animeToDelete = Optional.of(new Anime(title));

    when(repository.findByTitle(title)).thenReturn(animeToDelete);

    subject.handleSlackRequest(request);

    verify(repository, times(1)).delete(animeToDelete.get());
  }

  @Test
  void testHandleSlackRequest_Delete_ThrowsBadRequest() throws BadHttpRequest {
    final String title = "Fullmetal Alchemist";

    final String requestString = String.format("delete %s", title);
    final SlackRequest request = new SlackRequest(null, null, null, null, null, null, null, null, requestString, null);

    when(repository.findByTitle(title)).thenReturn(Optional.empty());

    assertThrows(BadHttpRequest.class, () -> subject.handleSlackRequest(request));
  }

  @Test
  void testHandleSlackRequest_ThrowBadRequest() throws BadHttpRequest {
    final String title = "Fullmetal Alchemist";

    final String requestString = String.format("foobar %s", title);
    final SlackRequest request = new SlackRequest(null, null, null, null, null, null, null, null, requestString, null);

    assertThrows(BadHttpRequest.class, () -> subject.handleSlackRequest(request));
  }
}