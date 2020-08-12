package com.pepper.weeabot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pepper.weeabot.entity.Anime;
import com.pepper.weeabot.model.SlackRequest;
import com.pepper.weeabot.repository.AnimeRepository;

import org.springframework.stereotype.Service;

import javassist.tools.web.BadHttpRequest;

@Service
public class SlackRequestService {
  
  final String emptyListResponse = "No anime here! Be the first to add one!";

  private final AnimeRepository repository;
  private final SlackResponseService responseService;

  public SlackRequestService(AnimeRepository repository, SlackResponseService responseService) {
    this.repository = repository;
    this.responseService = responseService;
  }

  public void handleSlackRequestData(SlackRequest data) throws BadHttpRequest {
    List<String> requestArgs = new ArrayList<String>(
      List.of(data.getText()
        .toLowerCase()
        .split("\\s+")
      )
    );
    
    switch (requestArgs.remove(0)) {
      case "list":
        sendAnimeListResponse(data);
        break;
      case "add":
        saveAnime(requestArgs);
        break;
      case "delete":
        deleteAnime(requestArgs);
        break;
      case "rate":
      default:
        throw new BadHttpRequest();
    }
  }


  private void sendAnimeListResponse(SlackRequest data) throws BadHttpRequest {
    String response = parseListRequest(repository.findAll());
    responseService.sendResponseToSlack(data, response);
  }

  private void saveAnime(List<String> requestArgs) throws BadHttpRequest {
    if (requestArgs.isEmpty()) { 
      throw new BadHttpRequest();
    }

    String title = getTitleFromRequestArgs(requestArgs);

    throwErrorIfAnimeExists(title);

    repository.save(new Anime(title));
  }

  private void deleteAnime(List<String> requestArgs) throws BadHttpRequest {
    if (requestArgs.isEmpty()) { 
      throw new BadHttpRequest();
    }

    String title = String.join(" ", requestArgs);
    try {
      Anime animeToDelete = repository.findByTitle(title).get();
      repository.delete(animeToDelete);
    } catch (Exception e) {
      throw new BadHttpRequest();
    }
  }

  private String parseListRequest(List<Anime> animeList) {
    StringBuilder animeListOutput = new StringBuilder();
    for (int i = 0; i < animeList.size(); i++) {
      animeListOutput.append(i + 1 + ". " + animeList.get(i).getTitle() + '\n');
    }
    String output = animeListOutput.toString().trim();
    return output.isEmpty() ? emptyListResponse : output;
  }

  private String getTitleFromRequestArgs(List<String> requestArgs) throws BadHttpRequest {
    return String.join(" ", requestArgs.subList(0, requestArgs.size())).strip();
  }

  private void throwErrorIfAnimeExists(String title) throws BadHttpRequest {
    Optional<Anime> anime = repository.findByTitle(title);
    if(anime.isPresent()) {
      throw new BadHttpRequest();
    }
  }
}