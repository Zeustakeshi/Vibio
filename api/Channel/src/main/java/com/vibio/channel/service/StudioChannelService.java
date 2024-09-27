/*
 *  StudioChannelService
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:55 PM
 * */

package com.vibio.channel.service;

import com.vibio.channel.dto.response.ChannelResponse;

public interface StudioChannelService {
	ChannelResponse getChannelInfo(String accountId);
}
