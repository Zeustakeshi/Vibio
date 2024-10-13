/*
 *  InternalChannelServiceImpl
 *  @author: Minhhieuano
 *  @created 9/29/2024 9:25 PM
 * */

package com.vibio.channel.service.impl;

import com.vibio.channel.dto.response.ChannelBasicResponse;
import com.vibio.channel.dto.response.ChannelDetailResponse;
import com.vibio.channel.exception.NotfoundException;
import com.vibio.channel.mapper.ChannelMapper;
import com.vibio.channel.model.Channel;
import com.vibio.channel.repository.ChannelRepository;
import com.vibio.channel.service.InternalChannelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalChannelServiceImpl implements InternalChannelService {

	private final ChannelRepository channelRepository;
	private final ChannelMapper channelMapper;

	@Override
	public boolean validateChannel(String channelId) {
		return channelRepository.existsById(channelId);
	}

	@Override
	public ChannelDetailResponse getChannelDetailInfo(String accountId) {
		Channel channel = channelRepository
				.findByAccountId(accountId)
				.orElseThrow(() -> new NotfoundException("Couldn't found channel with account id " + accountId));
		return channelMapper.channelToChannelDetailResponse(channel);
	}

	@Override
	public List<ChannelBasicResponse> getChannelByIds(List<String> channelIds) {
		List<Channel> channels = channelRepository.findAllByIdIn(channelIds);
		return channels.stream()
				.map(channelMapper::channelToChannelBasicResponse)
				.toList();
	}
}
