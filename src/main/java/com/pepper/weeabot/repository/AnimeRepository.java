package com.pepper.weeabot.repository;

import java.util.Optional;

import com.pepper.weeabot.entity.Anime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
  Optional<Anime> findByTitle(String title);
}