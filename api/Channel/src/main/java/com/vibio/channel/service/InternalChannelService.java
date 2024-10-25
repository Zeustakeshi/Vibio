/*
 *  InternalChannelService
 *  @author: Minhhieuano
 *  @created 9/29/2024 9:25 PM
 * */

package com.vibio.channel.service;

import com.vibio.channel.dto.response.ChannelBasicResponse;
import com.vibio.channel.dto.response.ChannelDetailResponse;
import com.vibio.channel.dto.response.ChannelResponse;
import java.util.List;

public interface InternalChannelService {
	boolean validateChannel(String channelId);

	ChannelDetailResponse getChannelDetailInfo(String accountId);

	ChannelResponse getChannelByAccountId(String accountId);

	List<ChannelBasicResponse> getChannelByIds(List<String> channelIds);
}
