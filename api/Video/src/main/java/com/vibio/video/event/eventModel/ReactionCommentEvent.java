/*
 *  ReactionCommentEvent
 *  @author: Minhhieuano
 *  @created 10/13/2024 3:15 PM
 * */

package com.vibio.video.event.eventModel;

import com.vibio.video.common.enums.ReactionType;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReactionCommentEvent extends Event {
	@Builder.Default
	private String id = "E_005";

	private String commentId;
	private String userId;
	private boolean isReaction;
	private ReactionType reactionType;
}
