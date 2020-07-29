package com.pepper.weeabot;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnimeController {
  private final AnimeRepository repository;

  AnimeController(AnimeRepository repository) {
    this.repository = repository;
  }

  @GetMapping("animes")
  List<Anime> getAnime() {
    return repository.findAll();
  }

  @GetMapping("animes/{id}")
  Optional<Anime> getAnime(@PathVariable Long id) {
    try {
      // TODO: Throw when null
      return repository.findById(id);
    } catch (Exception e) {
      throw e;
    }
  }

  @PatchMapping("animes/{id}")
  Anime updateAnime(@PathVariable Long id, @RequestBody Anime updatedAnime) {
    return repository.findById(id).map(anime -> {
      anime.setTitle(updatedAnime.getTitle());
      anime.updateRating((int)updatedAnime.getRating().floatValue());
      return repository.save(anime);
    }).orElseGet(() -> {
      return repository.save(updatedAnime);
    });
  }
} 