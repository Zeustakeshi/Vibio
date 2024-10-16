/*
 *  MailTemplateFactory
 *  @author: Minhhieuano
 *  @created 10/16/2024 9:38 AM
 * */

package com.vibio.notification.mail;

import com.vibio.notification.common.enums.MailType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailTemplateFactory {
	private final AccountCreationMailTemplate accountCreationMailTemplate;

	public MailTemplate getMailTemplate(MailType mailType) {
		switch (mailType) {
			case ACCOUNT_CREATION:
				return accountCreationMailTemplate;
			default:
				throw new IllegalArgumentException("Invalid mail type");
		}
	}
}
