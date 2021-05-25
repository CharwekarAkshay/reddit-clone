package com.coldcoder.reddit.mapper;

import com.coldcoder.reddit.dto.PostRequest;
import com.coldcoder.reddit.dto.PostResponse;
import com.coldcoder.reddit.model.Post;
import com.coldcoder.reddit.model.Subreddit;
import com.coldcoder.reddit.model.User;
import com.coldcoder.reddit.model.Vote;
import com.coldcoder.reddit.model.VoteType;
import com.coldcoder.reddit.repository.CommentRepository;
import com.coldcoder.reddit.repository.VoteRepository;
import com.coldcoder.reddit.service.AuthService;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static com.coldcoder.reddit.model.VoteType.UP_VOTE;

import java.util.Optional;

import static com.coldcoder.reddit.model.VoteType.DOWN_VOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findAllByPost(post).size();
    }

}
