package com.coldcoder.reddit.repository;

import com.coldcoder.reddit.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
}
