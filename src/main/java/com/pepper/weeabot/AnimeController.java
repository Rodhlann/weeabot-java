package com.pepper.weeabot;

import java.util.List;

import com.pepper.weeabot.utils.SlackRequestMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javassist.tools.web.BadHttpRequest;

@RestController
public class AnimeController {
  private final AnimeRepository repository;

  AnimeController(AnimeRepository repository) {
    this.repository = repository;
  }

  @GetMapping("weeabot")
  List<Anime> getAnime() {
    return repository.findAll();
  }

  @PostMapping("weeabot")
  void handleSlackRequest(@RequestParam String text) throws BadHttpRequest {
    Anime newAnime = SlackRequestMapper.mapRequest(text);
    repository.save(newAnime);
  }
} 