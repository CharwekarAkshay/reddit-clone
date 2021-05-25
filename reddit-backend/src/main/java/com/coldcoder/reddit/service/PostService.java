package com.coldcoder.reddit.service;

import com.coldcoder.reddit.dto.PostRequest;
import com.coldcoder.reddit.exception.SpringRedditException;
import com.coldcoder.reddit.exception.SubredditNotFoundException;
import com.coldcoder.reddit.mapper.PostMapper;
import com.coldcoder.reddit.model.Subreddit;
import com.coldcoder.reddit.repository.PostRepository;
import com.coldcoder.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AuthService authService;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findSubredditByName(postRequest.getSubredditName())
                           .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        User user = authService.getCurrentUser();
        postRepository.save(postMapper.map(postRequest, subreddit,));
    }
}
