/*
 *  ChannelService
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:15 PM
 * */

package com.vibio.channel.service;

import com.vibio.channel.dto.response.ChannelResponse;
import com.vibio.channel.event.eventModel.NewChannelEvent;
import com.vibio.channel.model.Channel;

public interface ChannelService {
    void createChannel(NewChannelEvent event);

    ChannelResponse getChannelById(String channelId, String accountId);

    ChannelResponse getChannelByIdGuest(String channelId);

    Channel findByAccountId(String accountId);
}
