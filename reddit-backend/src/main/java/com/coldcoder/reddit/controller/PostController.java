package com.coldcoder.reddit.controller;

import com.coldcoder.reddit.dto.PostRequest;
import com.coldcoder.reddit.dto.PostResponse;
import com.coldcoder.reddit.mapper.PostMapper;
import com.coldcoder.reddit.model.Post;
import com.coldcoder.reddit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.status(CREATED).body(postService.save(postRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                             .body(postService.getPost(id));

    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPost() {
        return ResponseEntity.status(OK)
                             .body(postService.getAllPosts());
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostBySubreddit(Long id) {
        return ResponseEntity.status(OK)
                             .body(postService.getPostsBySubreddit(id));
    }


    @GetMapping("/by-subreddit/{name}")
    public ResponseEntity<List<PostResponse>> getPostBySubreddit(String username) {
        return ResponseEntity.status(OK)
                             .body(postService.getPostsByUsername(username));
    }
}
