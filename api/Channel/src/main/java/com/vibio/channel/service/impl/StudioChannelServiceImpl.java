/*
 *  StudioChannelServiceImpl
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:56 PM
 * */

package com.vibio.channel.service.impl;

import com.vibio.channel.dto.request.UpdateChannelRequest;
import com.vibio.channel.dto.response.ChannelResponse;
import com.vibio.channel.exception.ConflictException;
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
        Channel channel = findChannelByAccountIdAndThrowException(accountId);
        return channelMapper.channelToChannelResponse(channel);
    }

    @Override
    public ChannelResponse updateChannelInfo(String accountId, UpdateChannelRequest request) {
        Channel channel = findChannelByAccountIdAndThrowException(accountId);

        if (channelRepository.existsByName(request.getName())) {
            throw new ConflictException("Channel with name " + request.getName() + " already existed!");
        }

        channel.setName(request.getName());
        channel.setDescription(request.getDescription());

        return channelMapper.channelToChannelResponse(channelRepository.save(channel));
    }

    private Channel findChannelByAccountIdAndThrowException(String accountId) {
        return channelRepository
                .findByAccountId(accountId)
                .orElseThrow(() -> new NotfoundException("Channel not found"));
    }
}
