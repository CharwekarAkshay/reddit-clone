package com.coldcoder.reddit.repository;

import com.coldcoder.reddit.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    Optional<Subreddit> findSubredditByName(String name);
}
