/*
 *  CommentReactionService
 *  @author: Minhhieuano
 *  @created 10/13/2024 2:37 PM
 * */

package com.vibio.video.service;

import com.vibio.video.common.enums.ReactionType;

public interface CommentReactionService {
	void reactionComment(String commentId, String accountId, ReactionType reactionType);

	void unReactionComment(String commentId, String accountId, ReactionType reactionType);
}
