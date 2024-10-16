/*
 *  StudioNotificationController
 *  @author: Minhhieuano
 *  @created 10/16/2024 8:14 AM
 * */

package com.vibio.notification.controller;

import com.vibio.notification.common.enums.MailType;
import com.vibio.notification.dto.response.ApiResponse;
import com.vibio.notification.service.MailService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudioNotificationController {

	private final MailService mailService;

	@GetMapping("/test")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<?> test() {
		mailService.sendMail(
				MailType.ACCOUNT_CREATION,
				Map.of(
						"name", "Minh Hiếu",
						"otp", "OTP_CODE"),
				"minh.hieu.a.n.o.n.y@gmail.com");
		return ApiResponse.success("Hello world");
	}
}
