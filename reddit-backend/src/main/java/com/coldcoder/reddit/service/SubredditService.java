package com.coldcoder.reddit.service;

import com.coldcoder.reddit.dto.SubredditDto;
import com.coldcoder.reddit.exception.SpringRedditException;
import com.coldcoder.reddit.mapper.SubredditMapper;
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
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditMapper.mapDtoToSubreddit(subredditDto);
        Subreddit save = subredditRepository.save(subreddit);
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                                  .stream()
                                  .map(subredditMapper::mapSubredditToDto)
                                  .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SpringRedditException("No subreddit found with id: " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
