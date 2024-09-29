/*
 *  InternalChannelService
 *  @author: Minhhieuano
 *  @created 9/29/2024 9:25 PM
 * */

package com.vibio.channel.service;

import com.vibio.channel.dto.response.ChannelResponse;

public interface InternalChannelService {
    boolean validateChannel(String channelId);

    ChannelResponse getChannelInfo(String accountId);
}
