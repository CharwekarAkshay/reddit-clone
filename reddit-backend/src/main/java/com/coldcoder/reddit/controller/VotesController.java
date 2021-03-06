package com.coldcoder.reddit.controller;

import com.coldcoder.reddit.dto.VoteDto;
import com.coldcoder.reddit.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VotesController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteDto> vote(@RequestBody VoteDto voteDto) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(voteService.vote(voteDto));
    }

}
