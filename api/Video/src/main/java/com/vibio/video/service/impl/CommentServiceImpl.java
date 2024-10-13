/*
 *  CommentServiceImpl
 *  @author: Minhhieuano
 *  @created 10/6/2024 8:59 AM
 * */

package com.vibio.video.service.impl;

import com.vibio.video.client.UserClient;
import com.vibio.video.dto.request.CommentRequest;
import com.vibio.video.dto.request.FindAccountsByIdsRequest;
import com.vibio.video.dto.request.UpdateCommentRequest;
import com.vibio.video.dto.response.CommentResponse;
import com.vibio.video.dto.response.PageableResponse;
import com.vibio.video.dto.response.UserResponse;
import com.vibio.video.entity.Comment;
import com.vibio.video.entity.Video;
import com.vibio.video.event.eventModel.NewCommentEvent;
import com.vibio.video.event.producer.CommentEventProducer;
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

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;
    private final CommentMapper commentMapper;
    private final UserClient userClient;
    private final PageMapper pageMapper;
    private final CommentEventProducer commentEventProducer;

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

        Comment newComment = commentRepository.save(comment);

        commentEventProducer.createNewComment(NewCommentEvent
                .builder()
                .commentId(newComment.getId())
                .parentId(request.getParentId())
                .videoId(videoId)
                .userId(accountId)
                .build()
        );

        CommentResponse commentResponse = commentMapper.commentToCommentResponse(newComment);

        commentResponse.setOwner(user);

        return commentResponse;
    }

    @Override
    public PageableResponse<CommentResponse> getAllGuestComment(String videoId, String parentId, int page, int limit) {
        Page<Comment> comments = fetchGuestComments(videoId, parentId, page, limit);

        List<UserResponse> users = Collections.emptyList();

        if (!comments.isEmpty()) users = fetchUsersForComments(comments);

        return mapCommentsToResponse(comments, users);
    }

    @Override
    public PageableResponse<CommentResponse> getAllComment(String videoId, String accountId, String parentId, int page, int limit) {
        Page<Comment> comments = fetchComments(videoId, parentId, page, limit);

        List<UserResponse> users = Collections.emptyList();

        if (!comments.isEmpty()) users = fetchUsersForComments(comments);

        return mapCommentsToResponse(comments, users);
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

    @Override
    public void updateReplyCount(String commentParentId, int count, boolean isIncrease) {
        Comment comment = commentRepository.findById(commentParentId)
                .orElseThrow(() -> new NotfoundException("Comment " + commentParentId + " not found"));

        if (isIncrease) comment.setReplyCount(comment.getReplyCount() + count);
        else comment.setReplyCount(comment.getReplyCount() - count);

        commentRepository.save(comment);
    }

    private Page<Comment> fetchGuestComments(String videoId, String parentId, int page, int limit) {
        if (parentId == null) {
            return commentRepository.getAllGuestComment(
                    videoId, PageRequest.of(page, limit, Sort.Direction.DESC, "updatedAt"));
        } else {
            return commentRepository.getAllGuestReplies(
                    videoId, parentId, PageRequest.of(page, limit, Sort.Direction.ASC, "createdAt"));
        }
    }

    private Page<Comment> fetchComments(String videoId, String parentId, int page, int limit) {
        if (parentId == null) {
            return commentRepository.getAllComment(
                    videoId, PageRequest.of(page, limit, Sort.Direction.DESC, "updatedAt"));
        } else {
            return commentRepository.getAllReplies(
                    videoId, parentId, PageRequest.of(page, limit, Sort.Direction.ASC, "createdAt"));
        }
    }

    private List<UserResponse> fetchUsersForComments(Page<Comment> comments) {
        List<String> userIds = comments.map(Comment::getUserId).stream().toList();
        return userClient.getUsersByIds(
                FindAccountsByIdsRequest.builder().ids(userIds).build()
        ).getData();
    }

    private PageableResponse<CommentResponse> mapCommentsToResponse(Page<Comment> comments, List<UserResponse> users) {
        return pageMapper.toPageableResponse(
                comments.map(comment -> mapCommentToResponse(comment, users))
        );
    }

    private CommentResponse mapCommentToResponse(Comment comment, List<UserResponse> users) {
        CommentResponse commentResponse = commentMapper.commentToCommentResponse(comment);
        UserResponse user = users.stream()
                .filter(u -> u.getId().equals(comment.getUserId()))
                .findFirst()
                .orElseThrow(() -> new NotfoundException("User " + comment.getUserId() + " not found"));
        commentResponse.setOwner(user);
        return commentResponse;
    }
}
