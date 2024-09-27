/*
 *  ChannelProducer
 *  @author: Minhhieuano
 *  @created 9/27/2024 10:30 PM
 * */

package com.vibio.user.event.producer;

import com.vibio.user.event.eventModel.NewChannelEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelProducer {
	private final KafkaTemplate<String, Object> channelTemplate;

	public void createNewChannel(NewChannelEvent event) {
		channelTemplate.send("new_channel", event);
	}
}
