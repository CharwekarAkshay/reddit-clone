package com.coldcoder.reddit.mapper;

import com.coldcoder.reddit.dto.VoteDto;
import com.coldcoder.reddit.model.Post;
import com.coldcoder.reddit.model.User;
import com.coldcoder.reddit.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    @Mapping(target = "voteId", ignore = true)
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Vote map(VoteDto voteDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(vote.getPost().getPostId())")
    VoteDto mapToDto(Vote vote);
}
