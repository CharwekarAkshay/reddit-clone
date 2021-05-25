package com.coldcoder.reddit.repository;

import java.util.List;

import com.coldcoder.reddit.model.Comment;
import com.coldcoder.reddit.model.Post;
import com.coldcoder.reddit.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByPost(Post post);

	List<Comment> findAllByUser(User user);
}
