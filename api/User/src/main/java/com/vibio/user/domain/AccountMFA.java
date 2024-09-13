/*
 *  AccountMFAConfirmation
 *  @author: Minhhieuano
 *  @created 9/12/2024 2:31 PM
 * */

package com.vibio.user.domain;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountMFA {
	@Builder.Default
	@Setter(AccessLevel.PRIVATE)
	private String code = NanoIdUtils.randomNanoId();

	private String email;
	private String otpCode;
}
