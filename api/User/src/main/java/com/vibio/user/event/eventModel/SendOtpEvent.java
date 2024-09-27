/*
 *  SendOtpEvent
 *  @author: Minhhieuano
 *  @created 9/27/2024 9:19 PM
 * */

package com.vibio.user.event.eventModel;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SendOtpEvent extends Event {
	@Builder.Default
	private String id = "E_001";

	private String otpCode;
	private String email;
}
