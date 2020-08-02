package com.pepper.weeabot;

import com.pepper.weeabot.models.Anime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {
  private final AnimeRepository repository;

  @Autowired
  public DatabaseLoader(AnimeRepository repository) {
    this.repository = repository;
  }

  @Override
  public void run(String... strings) throws Exception {
    int animeCount = repository.findAll().size();
    if (animeCount == 0) {
      this.repository.save(new Anime("attack on titan", 4F));
    }
  }
}
