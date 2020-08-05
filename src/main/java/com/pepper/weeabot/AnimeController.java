package com.pepper.weeabot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pepper.weeabot.models.Anime;
import com.pepper.weeabot.models.SlackRequest;
import com.pepper.weeabot.utils.SlackRequestMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javassist.tools.web.BadHttpRequest;

@RestController
public class AnimeController {
  private final AnimeRepository repository;

  AnimeController(AnimeRepository repository) {
    this.repository = repository;
  }

  @PostMapping("weeabot")
  void handleSlackRequest(@RequestBody SlackRequest data) throws BadHttpRequest {
    List<String> requestArgs = new ArrayList<String>(List.of(data.getText().split("\\s+")));

    switch(requestArgs.remove(0)) {
      case "list":
        respondToListRequest(data);
        break;
      case "add":
        repository.save(SlackRequestMapper.parseAddRequest(requestArgs));
        break;
      case "delete":
        String title = String.join(" ", requestArgs);
        try {
          Anime animeToDelete = repository.findByTitle(title).get();
          repository.delete(animeToDelete);
        } catch(Exception e) {
          throw new BadHttpRequest();
        }
        break;
      case "rate":
      default:
        throw new BadHttpRequest();
    }
  }

  private void respondToListRequest(SlackRequest data) throws BadHttpRequest {
    String response = SlackRequestMapper.parseListRequest(repository.findAll());

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth("WEEABOT SLACK TOKEN HERE");

    Map<String, Object> map = new HashMap<>();
    map.put("channel", data.getChannel_id());
    map.put("text", response);
    map.put("as_user", true);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

    restTemplate.postForEntity(data.getResponse_url(), entity, String.class);
  }
} 