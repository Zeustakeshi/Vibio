/*
 *  SubscriptionServiceImpl
 *  @author: Minhhieuano
 *  @created 10/10/2024 8:17 AM
 * */

package com.vibio.channel.service.impl;

import com.vibio.channel.dto.response.ChannelBasicResponse;
import com.vibio.channel.dto.response.PageableResponse;
import com.vibio.channel.dto.response.SubscriptionResponse;
import com.vibio.channel.exception.ConflictException;
import com.vibio.channel.exception.NotfoundException;
import com.vibio.channel.mapper.ChannelMapper;
import com.vibio.channel.mapper.PageMapper;
import com.vibio.channel.model.Channel;
import com.vibio.channel.model.Subscription;
import com.vibio.channel.repository.ChannelRepository;
import com.vibio.channel.repository.SubscriptionRepository;
import com.vibio.channel.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final ChannelRepository channelRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ChannelMapper channelMapper;
    private final PageMapper pageMapper;

    @Override
    public SubscriptionResponse subscribeChannel(String channelId, String accountId) {
        Channel channel =
                channelRepository.findById(channelId).orElseThrow(() -> new NotfoundException("Channel not found"));

        if (subscriptionRepository.existsByChannelIdAndUserId(channelId, accountId)) {
            throw new ConflictException("User has been subscribed to this channel.");
        }

        Subscription subscription =
                Subscription.builder().channel(channel).userId(accountId).build();

        subscriptionRepository.save(subscription);

        channel.setSubscribeCount(subscriptionRepository.countByChannelId(channelId));

        channelRepository.save(channel);

        return SubscriptionResponse.builder()
                .channelId(channelId)
                .userId(accountId)
                .subscribed(true)
                .build();
    }

    @Override
    public SubscriptionResponse unSubscribeChannel(String channelId, String accountId) {

        Channel channel =
                channelRepository.findById(channelId).orElseThrow(() -> new NotfoundException("Channel not found"));

        Subscription subscription = subscriptionRepository
                .findByChannelIdAndUserId(channelId, accountId)
                .orElseThrow(() -> new NotfoundException("User has not subscribed to this channel."));

        subscriptionRepository.delete(subscription);

        channel.setSubscribeCount(subscriptionRepository.countByChannelId(channelId));

        channelRepository.save(channel);

        return SubscriptionResponse.builder()
                .channelId(channelId)
                .userId(accountId)
                .subscribed(false)
                .build();
    }

    @Override
    public PageableResponse<ChannelBasicResponse> getSubscribedChannels(String accountId, int page, int limit) {
        PageRequest request = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "updatedAt"));

        Page<Subscription> subscriptions = subscriptionRepository.findAllByUserId(accountId, request);

        return pageMapper.toPageableResponse(subscriptions.map(sub -> channelMapper.channelToChannelBasicResponse(sub.getChannel())));
    }
}
