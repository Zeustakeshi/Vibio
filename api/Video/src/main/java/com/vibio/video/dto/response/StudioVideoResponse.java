/*
 *  VideoResponse
 *  @author: Minhhieuano
 *  @created 10/3/2024 9:42 AM
 * */

package com.vibio.video.dto.response;

import com.vibio.video.common.enums.Visibility;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudioVideoResponse {
	private String id;
	private String title;
	private String description;
	private String thumbnail;
	private Integer viewCount;
	private Integer likeCount;
	private Integer commentCount;
	private Integer dislikeCount;
	private Visibility visibility;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
