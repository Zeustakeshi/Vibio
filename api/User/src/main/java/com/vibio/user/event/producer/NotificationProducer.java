/*
 *  NotifyProducer
 *  @author: Minhhieuano
 *  @created 9/27/2024 9:34 PM
 * */

package com.vibio.user.event.producer;

import com.vibio.user.event.eventModel.SendOtpEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationProducer {
	private final KafkaTemplate<String, Object> notificationTemplate;

	public void sendOtp(SendOtpEvent event) {
		notificationTemplate.send("send_otp", event);
	}
}
