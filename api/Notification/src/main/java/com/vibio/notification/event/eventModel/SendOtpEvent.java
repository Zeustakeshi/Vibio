/*
 *  SendOtpEvent
 *  @author: Minhhieuano
 *  @created 9/27/2024 9:19 PM
 * */

package com.vibio.notification.event.eventModel;

import com.vibio.notification.common.enums.OtpType;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SendOtpEvent extends Event {
	private String id;
	private OtpType otpType;
	private String otpCode;
	private String email;
}
