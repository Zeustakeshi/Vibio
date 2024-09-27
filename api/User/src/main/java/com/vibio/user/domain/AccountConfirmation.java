/*
 *  AccountConfirmation
 *  @author: Minhhieuano
 *  @created 9/8/2024 9:25 PM
 * */

package com.vibio.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AccountConfirmation extends AccountOtpInformation {

	private String email;
	private String password;
	private String username;

	private String otpCode;
}
