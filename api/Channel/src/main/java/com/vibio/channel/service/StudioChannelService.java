/*
 *  StudioChannelService
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:55 PM
 * */

package com.vibio.channel.service;

import com.vibio.channel.dto.request.UpdateChannelRequest;
import com.vibio.channel.dto.response.ChannelResponse;

public interface StudioChannelService {
    ChannelResponse getChannelInfo(String accountId);

    ChannelResponse updateChannelInfo(String accountId, UpdateChannelRequest request);
}
