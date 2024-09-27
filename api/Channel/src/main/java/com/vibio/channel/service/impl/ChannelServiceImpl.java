/*
 *  ChannelServiceImpl
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:15 PM
 * */

package com.vibio.channel.service.impl;

import com.vibio.channel.event.eventModel.NewChannelEvent;
import com.vibio.channel.mapper.ChannelMapper;
import com.vibio.channel.repository.ChannelRepository;
import com.vibio.channel.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelServiceImpl implements ChannelService {
	private final ChannelRepository channelRepository;
	private final ChannelMapper channelMapper;

	@Override
	public void createChannel(NewChannelEvent event) {
		channelRepository.save(channelMapper.newChannelEventToChannel(event));
		log.info("Create new channel successfully!");
	}
}
