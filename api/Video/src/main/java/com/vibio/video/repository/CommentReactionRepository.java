/*
 *  CommentReactionRepository
 *  @author: Minhhieuano
 *  @created 10/13/2024 2:43 PM
 * */

package com.vibio.video.repository;

import com.vibio.video.common.enums.ReactionType;
import com.vibio.video.entity.CommentReaction;
import com.vibio.video.entity.key.CommentReactionKey;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReactionRepository extends JpaRepository<CommentReaction, CommentReactionKey> {
	@Query(
			"select count(reaction) > 0 from CommentReaction reaction where reaction.id.commentId = :commentId and reaction.id.userId = :userId and reaction.type = :reactionType")
	boolean existsByCommentIdAndUserIdAndReactionType(String commentId, String userId, ReactionType reactionType);

	@Query(
			"select reaction from CommentReaction reaction where reaction.id.commentId = :commentId and reaction.id.userId = :userId and reaction.type = :reactionType")
	Optional<CommentReaction> findByCommentIdAndUserIdAndReactionType(
			String commentId, String userId, ReactionType reactionType);

	@Query(
			"select count(*) from CommentReaction reaction where  reaction.id.commentId = :commentId and reaction.type = 'LIKE'")
	Integer countLikeByCommentId(String commentId);

	@Query(
			"select count(*) from CommentReaction reaction where  reaction.id.commentId = :commentId and reaction.type = 'DISLIKE'")
	Integer countDislikeByCommentId(String commentId);
}
