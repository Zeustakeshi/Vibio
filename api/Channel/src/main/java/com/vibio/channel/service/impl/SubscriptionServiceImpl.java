/*
 *  SubscriptionServiceImpl
 *  @author: Minhhieuano
 *  @created 10/10/2024 8:17 AM
 * */


package com.vibio.channel.service.impl;

import com.vibio.channel.dto.response.SubscriptionResponse;
import com.vibio.channel.exception.ConflictException;
import com.vibio.channel.exception.NotfoundException;
import com.vibio.channel.model.Channel;
import com.vibio.channel.model.Subscription;
import com.vibio.channel.repository.ChannelRepository;
import com.vibio.channel.repository.SubscriptionRepository;
import com.vibio.channel.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final ChannelRepository channelRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public SubscriptionResponse subscribeChannel(String channelId, String accountId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NotfoundException("Channel not found"));

        if (subscriptionRepository.existsByChannelIdAndUserId(channelId, accountId)) {
            throw new ConflictException("User has been subscribed to this channel.");
        }

        Subscription subscription = Subscription.builder()
                .channel(channel)
                .userId(accountId)
                .build();

        subscriptionRepository.save(subscription);
        return SubscriptionResponse.builder()
                .channelId(channelId)
                .userId(accountId)
                .subscribed(true)
                .build();
    }

    @Override
    public SubscriptionResponse unSubscribeChannel(String channelId, String accountId) {

        if (channelRepository.existsById(channelId)) {
            throw new NotfoundException("Channel not found");
        }
        Subscription subscription = subscriptionRepository.findByChannelIdAndUserId(channelId, accountId)
                .orElseThrow(() -> new NotfoundException("User has not subscribed to this channel."));

        subscriptionRepository.delete(subscription);

        return SubscriptionResponse.builder()
                .channelId(channelId)
                .userId(accountId)
                .subscribed(false)
                .build();
    }
}
