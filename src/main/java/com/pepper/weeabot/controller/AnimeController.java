package com.pepper.weeabot.controller;

import com.pepper.weeabot.model.SlackRequest;
import com.pepper.weeabot.service.SlackRequestService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javassist.tools.web.BadHttpRequest;

@RestController
public class AnimeController {
  
  private final SlackRequestService service;

  public AnimeController(SlackRequestService service) {
    this.service = service;
  }

  @PostMapping("/")
  public void handleSlackRequest(@RequestBody SlackRequest data) throws BadHttpRequest {
    service.handleSlackRequestData(data);
  }
}
