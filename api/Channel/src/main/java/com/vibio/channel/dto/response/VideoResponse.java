/*
 *  VideoResponse
 *  @author: Minhhieuano
 *  @created 10/7/2024 5:59 PM
 * */

package com.vibio.channel.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoResponse {
	private String id;
	private String title;
	private String thumbnail;

	@Builder.Default
	private int viewCount = 0;

	private LocalDateTime updatedAt;
}
