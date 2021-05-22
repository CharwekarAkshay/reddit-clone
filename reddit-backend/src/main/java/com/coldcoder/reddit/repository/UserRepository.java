package com.coldcoder.reddit.repository;

import com.coldcoder.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
