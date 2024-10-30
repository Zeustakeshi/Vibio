/*
 *  CommentServiceImpl
 *  @author: Minhhieuano
 *  @created 10/6/2024 8:59 AM
 * */

package com.vibio.video.service.impl;

import com.vibio.video.client.ChannelClient;
import com.vibio.video.client.UserClient;
import com.vibio.video.common.enums.ReactionType;
import com.vibio.video.dto.request.CheckMembershipRequest;
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
import com.vibio.video.repository.CommentReactionRepository;
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
    private final CommentReactionRepository commentReactionRepository;
    private final CommentMapper commentMapper;
    private final UserClient userClient;
    private final PageMapper pageMapper;
    private final CommentEventProducer commentEventProducer;
    private final ChannelClient channelClient;

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

        commentEventProducer.createNewComment(NewCommentEvent.builder()
                .commentId(newComment.getId())
                .parentId(request.getParentId())
                .videoId(videoId)
                .userId(accountId)
                .build());

        CommentResponse commentResponse = commentMapper.commentToCommentResponse(newComment);

        commentResponse.setOwner(user);

        return commentResponse;
    }

    @Override
    public PageableResponse<CommentResponse> getAllGuestComment(String videoId, String parentId, int page, int limit) {
        Page<Comment> comments = fetchGuestComments(videoId, parentId, page, limit);

        List<UserResponse> users = Collections.emptyList();

        List<String> members = Collections.emptyList();

        if (!comments.isEmpty()) {
            users = fetchUsersForComments(comments);

            String channelId = videoRepository.getChannelIdByVideoId(videoId).orElseThrow();

            members = fetchMemberForComments(channelId, users.stream().map(UserResponse::getId).toList());
        }


        return mapCommentsToResponse(comments, users, members, null);
    }

    @Override
    public PageableResponse<CommentResponse> getAllComment(
            String videoId, String accountId, String parentId, int page, int limit) {
        Page<Comment> comments = fetchComments(videoId, parentId, page, limit);

        List<UserResponse> users = Collections.emptyList();

        List<String> members = Collections.emptyList();

        if (!comments.isEmpty()) {
            users = fetchUsersForComments(comments);

            String channelId = videoRepository.getChannelIdByVideoId(videoId).orElseThrow();

            members = fetchMemberForComments(channelId, users.stream().map(UserResponse::getId).toList());
        }

        return mapCommentsToResponse(comments, users, members, accountId);
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
    public void updateReplyCount(String commentParentId) {
        Comment comment = commentRepository
                .findById(commentParentId)
                .orElseThrow(() -> new NotfoundException("Comment " + commentParentId + " not found"));

        comment.setReplyCount(commentRepository.countByParentId(commentParentId));

        commentRepository.save(comment);
    }

    @Override
    public void updateReactionCount(String commentId) {
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new NotfoundException("Comment " + commentId + " not found"));

        comment.setLikeCount(commentReactionRepository.countLikeByCommentId(commentId));
        comment.setDislikeCount(commentReactionRepository.countDislikeByCommentId(commentId));

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
        return userClient
                .getUsersByIds(FindAccountsByIdsRequest.builder().ids(userIds).build())
                .getData();
    }

    private List<String> fetchMemberForComments(String channelId, List<String> userIds) {
        return channelClient.checkMembershipStatus(new CheckMembershipRequest(channelId, userIds)).getData();
    }

    private PageableResponse<CommentResponse> mapCommentsToResponse(
            Page<Comment> comments, List<UserResponse> users, List<String> members, String accountId) {

        return pageMapper.toPageableResponse(comments.map(comment -> {
            boolean isMember = members.contains(comment.getUserId());
            return mapCommentToResponse(comment, users, isMember, accountId);
        }));
    }

    private CommentResponse mapCommentToResponse(Comment comment, List<UserResponse> users, boolean isMember, String accountId) {
        CommentResponse commentResponse = commentMapper.commentToCommentResponse(comment);
        UserResponse user = users.stream()
                .filter(u -> u.getId().equals(comment.getUserId()))
                .findFirst()
                .orElseThrow(() -> new NotfoundException("User " + comment.getUserId() + " not found"));

        if (accountId != null) {
            commentResponse.setLiked(commentReactionRepository.existsByCommentIdAndUserIdAndReactionType(
                    comment.getId(), accountId, ReactionType.LIKE));
            commentResponse.setDisliked(commentReactionRepository.existsByCommentIdAndUserIdAndReactionType(
                    comment.getId(), accountId, ReactionType.DISLIKE));
            commentResponse.setLovedByChannel(commentReactionRepository.existsByCommentIdAndUserIdAndReactionType(
                    comment.getId(), accountId, ReactionType.LOVE));
        }

        commentResponse.setOwner(user);
        commentResponse.setMember(isMember);

        return commentResponse;
    }
}
