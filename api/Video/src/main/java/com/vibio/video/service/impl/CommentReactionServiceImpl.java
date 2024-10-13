/*
 *  CommentReactionServiceImpl
 *  @author: Minhhieuano
 *  @created 10/13/2024 2:37 PM
 * */

package com.vibio.video.service.impl;

import com.vibio.video.common.enums.ReactionType;
import com.vibio.video.entity.Comment;
import com.vibio.video.entity.CommentReaction;
import com.vibio.video.entity.key.CommentReactionKey;
import com.vibio.video.event.eventModel.ReactionCommentEvent;
import com.vibio.video.event.producer.CommentEventProducer;
import com.vibio.video.exception.ConflictException;
import com.vibio.video.exception.NotfoundException;
import com.vibio.video.repository.CommentReactionRepository;
import com.vibio.video.repository.CommentRepository;
import com.vibio.video.service.CommentReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReactionServiceImpl implements CommentReactionService {
	private final CommentReactionRepository commentReactionRepository;
	private final CommentRepository commentRepository;
	private final CommentEventProducer commentEventProducer;

	@Override
	public void reactionComment(String commentId, String accountId, ReactionType reactionType) {
		Comment comment = commentRepository
				.findById(commentId)
				.orElseThrow(() -> new NotfoundException("Comment " + commentId + " not found"));

		if (reactionType.equals(ReactionType.LIKE)) likeComment(comment, accountId);
		else dislikeComment(comment, accountId);
	}

	@Override
	public void unReactionComment(String commentId, String accountId, ReactionType reactionType) {
		if (!commentRepository.existsById(commentId)) {
			throw new NotfoundException("Comment " + commentId + " not found");
		}

		if (reactionType.equals(ReactionType.LIKE)) unLikeComment(commentId, accountId);
		else unDislikeComment(commentId, accountId);
	}

	private void likeComment(Comment comment, String accountId) {
		if (commentReactionRepository.existsByCommentIdAndUserIdAndReactionType(
				comment.getId(), accountId, ReactionType.LIKE)) {
			throw new ConflictException("You've liked with this comment");
		}

		CommentReactionKey commentReactionKey = CommentReactionKey.builder()
				.commentId(comment.getId())
				.userId(accountId)
				.build();

		CommentReaction commentReaction = CommentReaction.builder()
				.comment(comment)
				.id(commentReactionKey)
				.type(ReactionType.LIKE)
				.build();

		commentReactionRepository.save(commentReaction);

		ReactionCommentEvent event = ReactionCommentEvent.builder()
				.isReaction(true)
				.reactionType(ReactionType.LIKE)
				.commentId(comment.getId())
				.userId(accountId)
				.build();
		commentEventProducer.reactionComment(event);

		unDislikeComment(comment.getId(), accountId);
	}

	private void dislikeComment(Comment comment, String accountId) {
		if (commentReactionRepository.existsByCommentIdAndUserIdAndReactionType(
				comment.getId(), accountId, ReactionType.DISLIKE)) {
			throw new ConflictException("You've disliked with this comment");
		}

		CommentReactionKey commentReactionKey = CommentReactionKey.builder()
				.commentId(comment.getId())
				.userId(accountId)
				.build();

		CommentReaction commentReaction = CommentReaction.builder()
				.comment(comment)
				.id(commentReactionKey)
				.type(ReactionType.DISLIKE)
				.build();

		commentReactionRepository.save(commentReaction);

		ReactionCommentEvent event = ReactionCommentEvent.builder()
				.isReaction(true)
				.reactionType(ReactionType.DISLIKE)
				.commentId(comment.getId())
				.userId(accountId)
				.build();
		commentEventProducer.reactionComment(event);

		unLikeComment(comment.getId(), accountId);
	}

	private void unLikeComment(String commentId, String accountId) {
		commentReactionRepository
				.findByCommentIdAndUserIdAndReactionType(commentId, accountId, ReactionType.LIKE)
				.ifPresent(commentReactionRepository::delete);

		ReactionCommentEvent event = ReactionCommentEvent.builder()
				.isReaction(false)
				.reactionType(ReactionType.LIKE)
				.commentId(commentId)
				.userId(accountId)
				.build();
		commentEventProducer.reactionComment(event);
	}

	private void unDislikeComment(String commentId, String accountId) {
		commentReactionRepository
				.findByCommentIdAndUserIdAndReactionType(commentId, accountId, ReactionType.DISLIKE)
				.ifPresent(commentReactionRepository::delete);

		ReactionCommentEvent event = ReactionCommentEvent.builder()
				.isReaction(false)
				.reactionType(ReactionType.DISLIKE)
				.commentId(commentId)
				.userId(accountId)
				.build();
		commentEventProducer.reactionComment(event);
	}
}
