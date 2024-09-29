/*
 *  InternalChannelServiceImpl
 *  @author: Minhhieuano
 *  @created 9/29/2024 9:25 PM
 * */


package com.vibio.channel.service.impl;

import com.vibio.channel.dto.response.ChannelResponse;
import com.vibio.channel.exception.NotfoundException;
import com.vibio.channel.mapper.ChannelMapper;
import com.vibio.channel.model.Channel;
import com.vibio.channel.repository.ChannelRepository;
import com.vibio.channel.service.InternalChannelService;
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
    public ChannelResponse getChannelInfo(String accountId) {
        Channel channel = channelRepository.findByAccountId(accountId)
                .orElseThrow(() -> new NotfoundException("Couldn't found channel with account id " + accountId));
        return channelMapper.channelToChannelResponse(channel);
    }
}
