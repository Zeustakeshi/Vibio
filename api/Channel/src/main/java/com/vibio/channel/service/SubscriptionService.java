/*
 *  SubscriptionService
 *  @author: Minhhieuano
 *  @created 10/10/2024 8:16 AM
 * */

package com.vibio.channel.service;

import com.vibio.channel.dto.response.ChannelBasicResponse;
import com.vibio.channel.dto.response.PageableResponse;
import com.vibio.channel.dto.response.SubscriptionResponse;

public interface SubscriptionService {
    SubscriptionResponse subscribeChannel(String channelId, String accountId);

    SubscriptionResponse unSubscribeChannel(String channelId, String accountId);

    PageableResponse<ChannelBasicResponse> getSubscribedChannels(String accountId, int page, int limit);
}
