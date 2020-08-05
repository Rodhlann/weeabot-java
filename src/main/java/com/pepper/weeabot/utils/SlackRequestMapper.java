package com.pepper.weeabot.utils;

import java.util.List;

import com.pepper.weeabot.models.Anime;

import javassist.tools.web.BadHttpRequest;

public class SlackRequestMapper {
  public static String emptyListResponse = "No anime here! Be the first to add one!";
  
  public static String parseListRequest(List<Anime> animeList) {
    StringBuilder animeListOutput = new StringBuilder();
    for(int i = 0; i < animeList.size(); i++) {
      animeListOutput.append(i + 1 + ". " + animeList.get(i).getTitle() + '\n');
    }
    String output = animeListOutput.toString().trim();
    return output.isEmpty() ? emptyListResponse : output;
  }
  
  public static Anime parseAddRequest(List<String> requestArgs) throws BadHttpRequest {
    float rating = getRatingFromRequestArgs(requestArgs);
    String title = getTitleFromRequestArgs(requestArgs);
    return new Anime(title, rating);
  } 

  private static String getTitleFromRequestArgs(List<String> requestArgs) throws BadHttpRequest{
    String title = String.join(" ", requestArgs.subList(0, requestArgs.size())).strip();
    if (title.isEmpty()) {
      throw new BadHttpRequest();
    } 
    return title;
  }

  private static Float getRatingFromRequestArgs(List<String> requestArgs) {
    int ratingIndex = requestArgs.size() - 1;
    try {
      Float rating = Float.valueOf(requestArgs.get(ratingIndex));
      requestArgs.remove(ratingIndex);
      return rating;
    } catch (NumberFormatException e) {
      return 0F;
    }
  }
}