package com.coldcoder.reddit.repository;

import com.coldcoder.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
