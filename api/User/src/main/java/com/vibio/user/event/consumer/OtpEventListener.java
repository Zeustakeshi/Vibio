/*
 *  OtpEventListener
 *  @author: Minhhieuano
 *  @created 9/27/2024 9:38 PM
 * */

package com.vibio.user.event.consumer;

import com.vibio.user.event.eventModel.SendOtpEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OtpEventListener {

	@KafkaListener(topics = "send_otp", groupId = "${spring.kafka.consumer.group-id}")
	void newOtpListener(SendOtpEvent event) {
		System.out.println("Hello world |:<.>:| () " + event.toString());
	}
}
