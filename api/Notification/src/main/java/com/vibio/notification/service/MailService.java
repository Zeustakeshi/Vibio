/*
 *  MainService
 *  @author: Minhhieuano
 *  @created 10/16/2024 8:36 AM
 * */

package com.vibio.notification.service;

import com.vibio.notification.common.enums.MailType;
import java.util.Map;

public interface MailService {
	void sendMail(MailType mailType, Map<String, Object> model, String recipient);
}
