/*
 *  MailTemplate
 *  @author: Minhhieuano
 *  @created 10/16/2024 9:22 AM
 * */

package com.vibio.notification.mail;

import java.util.Map;

public interface MailTemplate {
	String getSubject();

	String getContent(Map<String, Object> model);
}
