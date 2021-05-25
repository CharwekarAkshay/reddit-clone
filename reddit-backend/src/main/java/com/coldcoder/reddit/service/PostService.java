package com.coldcoder.reddit.service;

import com.coldcoder.reddit.dto.PostRequest;
import com.coldcoder.reddit.dto.PostResponse;
import com.coldcoder.reddit.exception.PostNotFoundException;
import com.coldcoder.reddit.exception.SubredditNotFoundException;
import com.coldcoder.reddit.mapper.PostMapper;
import com.coldcoder.reddit.model.Post;
import com.coldcoder.reddit.model.Subreddit;
import com.coldcoder.reddit.model.User;
import com.coldcoder.reddit.repository.PostRepository;
import com.coldcoder.reddit.repository.SubredditRepository;
import com.coldcoder.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final AuthService authService;

    public PostResponse save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findSubredditByName(postRequest.getSubredditName())
                                                 .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        User user = authService.getCurrentUser();
        Post post = postRepository.save(postMapper.map(postRequest, subreddit, user));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository
                .findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id:" + subredditId.toString()));
        List<Post> post = postRepository.findAllBySubreddit(subreddit);
        return post.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException("User not found with username:" + username));
        List<Post> post = postRepository.findAllByUser(user);
        return post.stream().map(postMapper::mapToDto).collect(toList());
    }
}
