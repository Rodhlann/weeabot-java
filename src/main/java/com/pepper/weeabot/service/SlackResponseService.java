package com.pepper.weeabot.service;

import java.util.HashMap;
import java.util.Map;

import com.pepper.weeabot.model.SlackRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SlackResponseService {
  
  public void sendResponseToSlack(SlackRequest data, String response) {
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