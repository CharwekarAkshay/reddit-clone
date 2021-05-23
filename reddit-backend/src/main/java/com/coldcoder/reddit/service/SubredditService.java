package com.coldcoder.reddit.service;

import com.coldcoder.reddit.dto.SubbredditDto;
import com.coldcoder.reddit.model.Subreddit;
import com.coldcoder.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SubredditService {
    private final SubredditRepository subredditRepository;

    @Transactional
    public SubbredditDto save(SubbredditDto subbredditDto) {
        Subreddit subreddit = mapSubredditDto(subbredditDto);
        Subreddit save = subredditRepository.save(subreddit);
        subbredditDto.setId(save.getId());
        return subbredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubbredditDto> getAll() {
        return subredditRepository.findAll()
                                  .stream()
                                  .map(this::mapToDto)
                                  .collect(Collectors.toList());
    }

    private SubbredditDto mapToDto(Subreddit subreddit) {
        return SubbredditDto.builder()
                            .name(subreddit.getName())
                            .id(subreddit.getId())
                            .numberOfPosts(subreddit.getPost().size())
                            .build();
    }

    private Subreddit mapSubredditDto(SubbredditDto subbredditDto) {
        return Subreddit.builder()
                        .name(subbredditDto.getName())
                        .description(subbredditDto.getDescription())
                        .build();
    }
}
