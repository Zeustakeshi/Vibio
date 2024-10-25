/*
 *  CommentEventProducer
 *  @author: Minhhieuano
 *  @created 10/12/2024 11:29 PM
 * */

package com.vibio.payment.event.producer;


import com.vibio.payment.event.eventModel.NewMemberEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelEventProducer {
    private final KafkaTemplate<String, Object> channelTemplate;

    public void newMember(NewMemberEvent event) {
        channelTemplate.send("new_member", event);
    }
}
