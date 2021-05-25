package com.coldcoder.reddit.service;

import com.coldcoder.reddit.dto.VoteDto;
import com.coldcoder.reddit.exception.PostNotFoundException;
import com.coldcoder.reddit.exception.SpringRedditException;
import com.coldcoder.reddit.mapper.VoteMapper;
import com.coldcoder.reddit.model.Post;
import com.coldcoder.reddit.model.Vote;
import com.coldcoder.reddit.model.VoteType;
import com.coldcoder.reddit.repository.PostRepository;
import com.coldcoder.reddit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.coldcoder.reddit.model.VoteType.UP_VOTE;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final VoteMapper voteMapper;
    private final AuthService authService;

    @Transactional
    public VoteDto vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                                  .orElseThrow(() -> new PostNotFoundException("Post Not found with ID -" + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                authService.getCurrentUser());

        // Checking if user has already UP_VOTED or DOWN+_VOTED
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }
        if (UP_VOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        postRepository.save(post);
        Vote vote = voteRepository.save(voteMapper.map(voteDto, post, authService.getCurrentUser()));
        return voteMapper.mapToDto(vote);
    }

}
