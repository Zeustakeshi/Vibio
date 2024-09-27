/*
 *  StudioChannelServiceImpl
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:56 PM
 * */

package com.vibio.channel.service.impl;

import com.vibio.channel.dto.response.ChannelResponse;
import com.vibio.channel.exception.NotfoundException;
import com.vibio.channel.mapper.ChannelMapper;
import com.vibio.channel.model.Channel;
import com.vibio.channel.repository.ChannelRepository;
import com.vibio.channel.service.StudioChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudioChannelServiceImpl implements StudioChannelService {

	private final ChannelRepository channelRepository;
	private final ChannelMapper channelMapper;

	@Override
	public ChannelResponse getChannelInfo(String accountId) {
		Channel channel = channelRepository
				.findByAccountId(accountId)
				.orElseThrow(() -> new NotfoundException("Channel not found"));
		return channelMapper.channelToChannelResponse(channel);
	}
}
