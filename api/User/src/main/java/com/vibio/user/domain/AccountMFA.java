/*
 *  AccountMFAConfirmation
 *  @author: Minhhieuano
 *  @created 9/12/2024 2:31 PM
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
public class AccountMFA extends AccountOtpInformation {
	private String email;
}
