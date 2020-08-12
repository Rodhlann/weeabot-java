package com.pepper.weeabot;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.pepper.weeabot.controller.AnimeController;
import com.pepper.weeabot.model.SlackRequest;
import com.pepper.weeabot.service.SlackRequestService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javassist.tools.web.BadHttpRequest;

@ExtendWith(MockitoExtension.class)
class AnimeControllerTests {

  @InjectMocks
  AnimeController subject;

  @Mock
  SlackRequestService requestService;

  @Test
  void testHandleSlackRequest() throws BadHttpRequest {
    subject.handleSlackRequest(new SlackRequest());
    verify(requestService).handleSlackRequestData(any());
  }
}