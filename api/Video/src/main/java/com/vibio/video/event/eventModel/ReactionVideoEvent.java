/*
 *  ReactionVideoEvent
 *  @author: Minhhieuano
 *  @created 10/13/2024 12:01 PM
 * */

package com.vibio.video.event.eventModel;

import com.vibio.video.common.enums.ReactionType;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReactionVideoEvent extends Event {

	@Builder.Default
	private String id = "E_004";

	private String videoId;
	private String userId;
	private boolean isReaction;
	private ReactionType reactionType;
}
