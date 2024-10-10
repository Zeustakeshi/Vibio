/*
 *  CommentResponse
 *  @author: Minhhieuano
 *  @created 10/6/2024 9:10 AM
 * */

package com.vibio.video.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponse {
	private String id;

	private String content;

	private UserResponse owner;

	private boolean updated;

	@Builder.Default
	private Integer likeCount = 0;

	@Builder.Default
	private Integer replyCount = 0;

	private LocalDateTime updatedAt;
}