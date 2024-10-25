/*
 *  PaymentConsumer
 *  @author: Minhhieuano
 *  @created 10/25/2024 12:46 PM
 * */


package com.vibio.channel.event.consumer;


import com.vibio.channel.event.eventModel.NewMemberEvent;
import com.vibio.channel.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {
    private final MemberService memberService;

    @KafkaListener(topics = "new_member", groupId = "${spring.kafka.consumer.group-id}")
    public void NewMemberEventListener(NewMemberEvent event) {
        memberService.createNewMember(event.getChannelId(), event.getMemberId(), event.getPaymentId());
    }
}
