/*
 *  AccountMFAConfirmation
 *  @author: Minhhieuano
 *  @created 9/12/2024 2:31 PM
 * */

package com.vibio.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountMFA extends AccountOtpInformation {
    private String email;
}
