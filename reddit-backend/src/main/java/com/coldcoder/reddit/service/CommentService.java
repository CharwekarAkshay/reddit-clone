package com.coldcoder.reddit.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.transaction.Transactional;

import com.coldcoder.reddit.dto.CommentsDto;
import com.coldcoder.reddit.exception.PostNotFoundException;
import com.coldcoder.reddit.mapper.CommentMapper;
import com.coldcoder.reddit.model.Comment;
import com.coldcoder.reddit.model.NotificationEmail;
import com.coldcoder.reddit.model.Post;
import com.coldcoder.reddit.model.User;
import com.coldcoder.reddit.repository.CommentRepository;
import com.coldcoder.reddit.repository.PostRepository;
import com.coldcoder.reddit.repository.UserRepository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentService {

	private PostRepository postRepository;
	private CommentRepository commentRepository;
	private UserRepository userRepository;
	private CommentMapper commentMapper;
	private MailContentBuilder mailContentBuilder;
	private AuthService authService;
	private MailService mailService;

	public CommentsDto createComment(CommentsDto commentsDto) {
		Post post = postRepository.findById(commentsDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException("No post found with id: " + commentsDto.getPostId()));
		Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());

		// * Send mail to respected user that they have got comment on their post
		String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post");
		sendCommentNotification(message, post.getUser());

		return commentMapper.mapToDto(commentRepository.save(comment));
	}

	public List<CommentsDto> getCommentsByPost(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Post not found with id" + postId.toString()));

		return commentRepository.findAllByPost(post).stream().map(commentMapper::mapToDto).collect(toList());
	}

	public List<CommentsDto> getCommentsByUser(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found with exception" + username));
		return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(toList());
	}

	public void sendCommentNotification(String message, User user) {
		mailService.sendMail(
				new NotificationEmail(user.getUsername() + "Commented on your post", user.getEmail(), message));
	}

}
