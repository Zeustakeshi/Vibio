/*
 *  VideoResponse
 *  @author: Minhhieuano
 *  @created 10/7/2024 5:59 PM
 * */


package com.vibio.video.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VideoResponse {
    private String id;
    private ChannelResponse channel;
    private String title;
    private String thumbnail;
    @Builder.Default
    private int viewCount = 0;
    private LocalDateTime updatedAt;
}
