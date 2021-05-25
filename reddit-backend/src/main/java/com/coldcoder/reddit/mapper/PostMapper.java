package com.coldcoder.reddit.mapper;

import com.coldcoder.reddit.dto.PostRequest;
import com.coldcoder.reddit.dto.PostResponse;
import com.coldcoder.reddit.model.Post;
import com.coldcoder.reddit.model.Subreddit;
import com.coldcoder.reddit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    public abstract PostResponse mapToDto(Post post);

}
