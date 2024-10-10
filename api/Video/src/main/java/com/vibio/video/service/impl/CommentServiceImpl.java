/*
 *  CommentServiceImpl
 *  @author: Minhhieuano
 *  @created 10/6/2024 8:59 AM
 * */

package com.vibio.video.service.impl;

import com.vibio.video.client.UserClient;
import com.vibio.video.dto.request.CommentRequest;
import com.vibio.video.dto.request.UpdateCommentRequest;
import com.vibio.video.dto.response.CommentResponse;
import com.vibio.video.dto.response.PageableResponse;
import com.vibio.video.dto.response.UserResponse;
import com.vibio.video.entity.Comment;
import com.vibio.video.entity.Video;
import com.vibio.video.exception.ForbiddenException;
import com.vibio.video.exception.NotfoundException;
import com.vibio.video.mapper.CommentMapper;
import com.vibio.video.mapper.PageMapper;
import com.vibio.video.repository.CommentRepository;
import com.vibio.video.repository.VideoRepository;
import com.vibio.video.service.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentRepository commentRepository;
	private final VideoRepository videoRepository;
	private final CommentMapper commentMapper;
	private final UserClient userClient;
	private final PageMapper pageMapper;

	@Override
	public CommentResponse crateComment(String videoId, String accountId, CommentRequest request) {
		Video video = videoRepository.findById(videoId).orElseThrow(() -> new NotfoundException("Video not found"));

		if (!video.isAllowedComment()) {
			throw new ForbiddenException("Video not allow comment");
		}

		// get user by accountId
		UserResponse user = userClient.getUserByAccountId(accountId).getData();

		Comment comment = Comment.builder()
				.content(request.getContent())
				.userId(user.getId())
				.video(video)
				.build();

		if (request.getParentId() != null) {
			Comment parent = commentRepository
					.findById(request.getParentId())
					.orElseThrow(() -> new NotfoundException("Comment not found"));
			if (parent.isReply()) comment.setParent(parent.getParent());
			else comment.setParent(parent);
			comment.setReply(true);
		}

		CommentResponse commentResponse = commentMapper.commentToCommentResponse(commentRepository.save(comment));

		commentResponse.setOwner(user);

		return commentResponse;
	}

	@Override
	public PageableResponse<CommentResponse> getAllGuestComment(String videoId, String parentId, int page, int limit) {

		Page<Comment> comments;

		if (parentId == null) {
			comments = commentRepository.getAllGuestComment(
					videoId, PageRequest.of(page, limit, Sort.Direction.DESC, "updatedAt"));
		} else {
			comments = commentRepository.getAllGuestReplies(
					videoId, parentId, PageRequest.of(page, limit, Sort.Direction.ASC, "createdAt"));
		}

		return pageMapper.toPageableResponse(comments.map(commentMapper::commentToCommentResponse));
	}

	@Override
	public PageableResponse<CommentResponse> getAllComment(
			String videoId, String accountId, String parentId, int page, int limit) {

		Page<Comment> comments;

		if (parentId == null) {
			comments = commentRepository.getAllComment(
					videoId, PageRequest.of(page, limit, Sort.Direction.DESC, "updatedAt"));
		} else {
			comments = commentRepository.getAllReplies(
					videoId, parentId, PageRequest.of(page, limit, Sort.Direction.ASC, "createdAt"));
		}

		return pageMapper.toPageableResponse(comments.map(commentMapper::commentToCommentResponse));
	}

	@Override
	public CommentResponse updateComment(
			String videoId, String accountId, String commentId, UpdateCommentRequest request) {
		Comment comment = commentRepository
				.findByIdAndUserId(commentId, accountId)
				.orElseThrow(() -> new NotfoundException("Comment not found"));
		comment.setContent(request.getContent());
		comment.setUpdated(true);
		return commentMapper.commentToCommentResponse(commentRepository.save(comment));
	}

	@Override
	@Transactional
	public boolean deleteComment(String videoId, String accountId, String commentId) {
		commentRepository.deleteByIdAndUserIdAndVideoId(commentId, accountId, videoId);
		return true;
	}
}
