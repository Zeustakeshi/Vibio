/*
 *  ChannelConsumer
 *  @author: Minhhieuano
 *  @created 9/27/2024 10:32 PM
 * */

package com.vibio.channel.event.consumer;

import com.vibio.channel.event.eventModel.NewChannelEvent;
import com.vibio.channel.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelConsumer {

    private final ChannelService channelService;

    @KafkaListener(topics = "new_channel", groupId = "${spring.kafka.consumer.group-id}")
    public void NewChannelListener(NewChannelEvent event) {
        channelService.createChannel(event);
    }
}
