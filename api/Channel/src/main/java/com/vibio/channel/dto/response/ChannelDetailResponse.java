/*
 *  ChannelResponse
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:52 PM
 * */

package com.vibio.channel.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChannelDetailResponse {
    private String id;
    private String name;
    private String description;
    private String thumbnail;
    private String background;
    private String defaultEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
