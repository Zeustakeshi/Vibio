/*
 *  SendOtpEvent
 *  @author: Minhhieuano
 *  @created 9/27/2024 9:19 PM
 * */

package com.vibio.user.event.eventModel;

import com.vibio.user.common.enums.OtpType;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SendOtpEvent extends Event {
	@Builder.Default
	private String id = "E_001";

	private OtpType otpType;
	private String otpCode;
	private String email;
}
