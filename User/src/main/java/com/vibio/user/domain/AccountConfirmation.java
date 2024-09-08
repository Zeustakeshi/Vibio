/*
 *  AccountConfirmation
 *  @author: Minhhieuano
 *  @created 9/8/2024 9:25 PM
 * */

package com.vibio.user.domain;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountConfirmation {
	@Builder.Default
	@Setter(AccessLevel.PRIVATE)
	private String code = NanoIdUtils.randomNanoId();

	private String email;
	private String password;
	private String username;
	private String otpCode;
	private int resendCount;
}
