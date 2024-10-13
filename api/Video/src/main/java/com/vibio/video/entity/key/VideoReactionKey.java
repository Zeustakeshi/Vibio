/*
 *  ReactionKey
 *  @author: Minhhieuano
 *  @created 10/6/2024 8:49 AM
 * */

package com.vibio.video.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoReactionKey implements Serializable {

	@Column(name = "user_id")
	private String userId;

	@Column(name = "video_id")
	private String videoId;
}
