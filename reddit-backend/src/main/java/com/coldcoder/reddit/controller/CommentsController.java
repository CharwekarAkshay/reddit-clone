package com.coldcoder.reddit.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import com.coldcoder.reddit.dto.CommentsDto;
import com.coldcoder.reddit.service.CommentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {
	private CommentService commentService;

	@PostMapping
	public ResponseEntity<CommentsDto> createComment(@RequestBody CommentsDto commentsDto) {
		return ResponseEntity.status(CREATED).body(commentService.createComment(commentsDto));
	}

	@GetMapping("/by-post/{postId}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsFromPost(@RequestParam("id") Long postId) {
		return ResponseEntity.status(OK).body(commentService.getCommentsByPost(postId));
	}

	@GetMapping("/by-user/{userName}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@RequestParam("userName") String userName) {
		return ResponseEntity.status(OK).body(commentService.getCommentsByUser(userName));
	}
}
