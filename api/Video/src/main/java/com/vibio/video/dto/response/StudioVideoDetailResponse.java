/*
 *  VideoDetailResponse
 *  @author: Minhhieuano
 *  @created 10/3/2024 10:10 AM
 * */

package com.vibio.video.dto.response;

import com.vibio.video.common.enums.UploadStatus;
import com.vibio.video.common.enums.Visibility;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudioVideoDetailResponse {

	private String id;

	private String title;

	private String description;

	private String url;

	private String thumbnail;

	@Builder.Default
	private Integer viewCount = 0;

	@Builder.Default
	private Integer likeCount = 0;

	@Builder.Default
	private Integer dislikeCount = 0;

	private List<String> tags;

	private UploadStatus uploadStatus;

	private Visibility visibility;

	private boolean allowedComment;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
