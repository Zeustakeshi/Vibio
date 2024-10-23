/*
 *  ChannelResponse
 *  @author: Minhhieuano
 *  @created 10/19/2024 11:06 PM
 * */


package com.vibio.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelResponse {
    private String id;
    private String thumbnail;
    private String name;
    private String accountId;
}

