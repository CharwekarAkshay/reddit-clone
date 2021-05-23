package com.coldcoder.reddit.controller;

import com.coldcoder.reddit.dto.SubbredditDto;
import com.coldcoder.reddit.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubbredditDto> createSubreddit(@RequestBody SubbredditDto subbredditDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(subredditService.save(subbredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubbredditDto>> getAllSubreddits() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(subredditService.getAll());
    }
}