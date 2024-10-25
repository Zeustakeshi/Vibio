/*
 *  PlaylistVideoResponse
 *  @author: Minhhieuano
 *  @created 10/17/2024 7:36 PM
 * */

package com.vibio.channel.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaylistVideoResponse {
	private String id;
	private String title;
	private String thumbnail;

	private Integer viewCount;

	private Integer order;

	private LocalDateTime updatedAt;
}
