package com.pepper.weeabot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pepper.weeabot.entity.Anime;
import com.pepper.weeabot.model.SlackRequest;
import com.pepper.weeabot.repository.AnimeRepository;
import com.pepper.weeabot.service.SlackRequestService;
import com.pepper.weeabot.service.SlackResponseService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javassist.tools.web.BadHttpRequest;

@ExtendWith(MockitoExtension.class)
public class SlackRequestServiceTests {

  @InjectMocks
  SlackRequestService subject;
  @Mock
  AnimeRepository repository;
  @Mock
  SlackResponseService responseService;
  @Captor
  ArgumentCaptor<Anime> captor;

  @Test
  void testHandleSlackRequestData_List() throws BadHttpRequest {
    String responseString = "1. Fullmetal Alchemist\n2. One Punch Man";

    final SlackRequest request = new SlackRequest();
    request.setChannel_id("1");
    request.setResponse_url("https://www.test.com");
    request.setText("list");

    List<Anime> animeList = new ArrayList<>();
    animeList.add(new Anime("Fullmetal Alchemist"));
    animeList.add(new Anime("One Punch Man"));

    when(repository.findAll()).thenReturn(animeList);

    subject.handleSlackRequestData(request);

    verify(responseService).sendResponseToSlack(request, responseString);
  }

  @Test
  void testHandleSlackRequestData_List_NoAnimeFound() throws BadHttpRequest {
    String responseString = "No anime here! Be the first to add one!";

    final SlackRequest request = new SlackRequest();
    request.setChannel_id("1");
    request.setResponse_url("https://www.test.com");
    request.setText("list");

    List<Anime> animeList = new ArrayList<>();

    when(repository.findAll()).thenReturn(animeList);

    subject.handleSlackRequestData(request);

    verify(responseService).sendResponseToSlack(request, responseString);
  }

  @Test
  void testHandleSlackRequestData_Add() throws BadHttpRequest {
    final String title = "Fullmetal Alchemist";

    final String requestString = String.format("add %s", title);
    final SlackRequest request = new SlackRequest();
    request.setText(requestString);

    subject.handleSlackRequestData(request);

    verify(repository).save(captor.capture());
    assertEquals(title, captor.getValue().getTitle());
    assertEquals(0, captor.getValue().getRatingCount());
  }

  @Test
  void testHandleSlackRequestData_Add_NoData() throws BadHttpRequest {
    final String title = "";

    final String requestString = String.format("add %s", title);
    final SlackRequest request = new SlackRequest();
    request.setText(requestString);

    assertThrows(BadHttpRequest.class, () -> subject.handleSlackRequestData(request));
  }

  @Test
  void testHandleSlackRequestData_Delete() throws BadHttpRequest {
    final String title = "Fullmetal Alchemist";

    final String requestString = String.format("delete %s", title);
    final SlackRequest request = new SlackRequest();
    request.setText(requestString);

    final Optional<Anime> animeToDelete = Optional.of(new Anime(title));

    when(repository.findByTitle(title)).thenReturn(animeToDelete);

    subject.handleSlackRequestData(request);

    verify(repository).delete(animeToDelete.get());
  }

  @Test
  void testHandleSlackRequestData_Delete_NoAnimeFound() throws BadHttpRequest {
    final String title = "Fullmetal Alchemist";

    final String requestString = String.format("delete %s", title);
    final SlackRequest request = new SlackRequest();
    request.setText(requestString);

    when(repository.findByTitle(title)).thenReturn(Optional.empty());

    assertThrows(BadHttpRequest.class, () -> subject.handleSlackRequestData(request));
  }

  @Test
  void testHandleSlackRequestData_Delete_NoData() throws BadHttpRequest {
    final String title = "";

    final String requestString = String.format("delete %s", title);
    final SlackRequest request = new SlackRequest();
    request.setText(requestString);

    assertThrows(BadHttpRequest.class, () -> subject.handleSlackRequestData(request));
  }

  @Test
  void testHandleSlackRequestData_ThrowBadRequest() throws BadHttpRequest {
    final String title = "Fullmetal Alchemist";

    final String requestString = String.format("foobar %s", title);
    final SlackRequest request = new SlackRequest();
    request.setText(requestString);

    assertThrows(BadHttpRequest.class, () -> subject.handleSlackRequestData(request));
  }
}