/*
 *  AccountConfirmation
 *  @author: Minhhieuano
 *  @created 9/8/2024 9:25 PM
 * */

package com.vibio.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountConfirmation extends AccountOtpInformation {

    private String email;
    private String password;
    private String username;

    private String otpCode;

}
