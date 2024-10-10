/*
 *  ChannelResponse
 *  @author: Minhhieuano
 *  @created 10/7/2024 6:03 PM
 * */


package com.vibio.channel.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelBasicResponse {
    private String id;
    private String name;
    private String thumbnail;
}
