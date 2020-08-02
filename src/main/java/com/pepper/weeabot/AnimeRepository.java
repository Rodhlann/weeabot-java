package com.pepper.weeabot;

import com.pepper.weeabot.models.Anime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

}