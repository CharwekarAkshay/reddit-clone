package com.coldcoder.reddit.service;

import com.coldcoder.reddit.dto.SubredditDto;
import com.coldcoder.reddit.model.Subreddit;
import com.coldcoder.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {
    private final SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = mapSubredditDto(subredditDto);
        Subreddit save = subredditRepository.save(subreddit);
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                                  .stream()
                                  .map(this::mapToDto)
                                  .collect(Collectors.toList());
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder()
                           .name(subreddit.getName())
                           .id(subreddit.getId())
                           .numberOfPosts(subreddit.getPost().size())
                           .build();
    }

    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
        return Subreddit.builder()
                        .name(subredditDto.getName())
                        .description(subredditDto.getDescription())
                        .build();
    }
}
