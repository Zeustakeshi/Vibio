/*
 *  VideoChannelResponse
 *  @author: Minhhieuano
 *  @created 10/7/2024 6:00 PM
 * */

package com.vibio.video.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelResponse {
    private String id;
    private String thumbnail;
    private String name;
}
