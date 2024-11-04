/*
 *  VideoDetailResponse
 *  @author: Minhhieuano
 *  @created 10/9/2024 12:02 AM
 * */

package com.vibio.video.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VideoDetailResponse {
    private String id;
    private String channelId;
    private String title;
    private String url;
    private String thumbnail;
    private String description;
    private Integer viewCount;
    private boolean isLiked;
    private boolean isDisliked;
    private Integer likeCount;
    private Integer commentCount;
    private boolean allowedComment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
