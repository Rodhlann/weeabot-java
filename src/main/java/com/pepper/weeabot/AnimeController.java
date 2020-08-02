package com.pepper.weeabot;

import java.util.List;

import com.pepper.weeabot.models.Anime;
import com.pepper.weeabot.models.SlackRequest;
import com.pepper.weeabot.utils.SlackRequestMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  void handleSlackRequest(@RequestBody SlackRequest data) throws BadHttpRequest {
    Anime newAnime = SlackRequestMapper.mapRequest(data.getText());
    repository.save(newAnime);
  }
} 