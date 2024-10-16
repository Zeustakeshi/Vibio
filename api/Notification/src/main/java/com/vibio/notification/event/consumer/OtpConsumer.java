/*
 *  OtpConsumer
 *  @author: Minhhieuano
 *  @created 10/16/2024 10:10 AM
 * */

package com.vibio.notification.event.consumer;

import com.vibio.notification.common.enums.MailType;
import com.vibio.notification.common.enums.OtpType;
import com.vibio.notification.event.eventModel.SendOtpEvent;
import com.vibio.notification.service.MailService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OtpConsumer {
	private final MailService mailService;

	@KafkaListener(topics = "send_otp", groupId = "${spring.kafka.consumer.group-id}")
	public void AccountCreationOtpListener(SendOtpEvent event) {
		if (event.getOtpType().equals(OtpType.ACCOUNT_CREATION)) {
			mailService.sendMail(MailType.ACCOUNT_CREATION, Map.of("otpCode", event.getOtpCode()), event.getEmail());
		}
	}
}
