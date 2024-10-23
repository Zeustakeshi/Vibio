/*
 *  ChannelServiceImpl
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:15 PM
 * */

package com.vibio.channel.service.impl;

import com.vibio.channel.dto.response.ChannelResponse;
import com.vibio.channel.event.eventModel.NewChannelEvent;
import com.vibio.channel.exception.NotfoundException;
import com.vibio.channel.mapper.ChannelMapper;
import com.vibio.channel.model.Channel;
import com.vibio.channel.repository.ChannelRepository;
import com.vibio.channel.repository.SubscriptionRepository;
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
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void createChannel(NewChannelEvent event) {
        channelRepository.save(channelMapper.newChannelEventToChannel(event));
        log.info("Create new channel successfully!");
    }

    @Override
    public ChannelResponse getChannelById(String channelId, String accountId) {
        Channel channel =
                channelRepository.findById(channelId).orElseThrow(() -> new NotfoundException("Channel not found"));

        ChannelResponse channelResponse = channelMapper.channelToChannelResponse(channel);
        channelResponse.setSubscribed(subscriptionRepository.existsByChannelIdAndUserId(channelId, accountId));
        return channelResponse;
    }

    @Override
    public ChannelResponse getChannelByIdGuest(String channelId) {
        Channel channel =
                channelRepository.findById(channelId).orElseThrow(() -> new NotfoundException("Channel not found"));
        return channelMapper.channelToChannelResponse(channel);
    }

    @Override
    public Channel findByAccountId(String accountId) {
        return channelRepository.findByAccountId(accountId)
                .orElseThrow(() -> new NotfoundException("Channel not found"));
    }
}
