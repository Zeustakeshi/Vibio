/*
 *  AccountCreationMailTemplate
 *  @author: Minhhieuano
 *  @created 10/16/2024 9:22 AM
 * */

package com.vibio.notification.mail;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class AccountCreationMailTemplate implements MailTemplate {

	private final TemplateEngine templateEngine;

	@Override
	public String getSubject() {
		return "New account OTP";
	}

	@Override
	public String getContent(Map<String, Object> model) {
		Context context = new Context();
		context.setVariables(model);
		return templateEngine.process("account-creation", context);
	}
}
