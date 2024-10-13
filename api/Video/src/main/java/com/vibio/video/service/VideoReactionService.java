/*
 *  VideoReactionService
 *  @author: Minhhieuano
 *  @created 10/13/2024 10:33 AM
 * */

package com.vibio.video.service;

import com.vibio.video.common.enums.ReactionType;

public interface VideoReactionService {
	void reactionVideo(String videoId, String accountId, ReactionType reactionType);

	void unReactionVideo(String videoId, String accountId, ReactionType reactionType);
}
