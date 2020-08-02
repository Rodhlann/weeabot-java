package com.pepper.weeabot.utils;

import java.util.List;

import com.pepper.weeabot.models.Anime;

import javassist.tools.web.BadHttpRequest;

public class SlackRequestMapper {

  private static Anime parseAddRequest(List<String> requestArgs) {
    String title = String.join(" ", requestArgs.subList(1, requestArgs.size() - 1));
    float rating = Float.parseFloat(requestArgs.get(requestArgs.size() - 1));
    return new Anime(title, rating);
  }

  public static Anime mapRequest(String requestString) throws BadHttpRequest {

    List<String> requestArgs = List.of(requestString.split("\\s+"));

    if (requestArgs.contains("add")) {
      return parseAddRequest(requestArgs);
    }

    throw new BadHttpRequest();
  } 
}