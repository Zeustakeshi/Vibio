/*
 *  StudioChannelService
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:55 PM
 * */

package com.vibio.channel.service;

import com.vibio.channel.dto.request.UpdateChannelRequest;
import com.vibio.channel.dto.response.ChannelDetailResponse;

public interface StudioChannelService {
	ChannelDetailResponse getChannelInfo(String accountId);

	ChannelDetailResponse updateChannelInfo(String accountId, UpdateChannelRequest request);
}
