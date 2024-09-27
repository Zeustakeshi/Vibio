/*
 *  AccountOtpInfomation
 *  @author: Minhhieuano
 *  @created 9/27/2024 12:20 PM
 * */


package com.vibio.user.domain;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class AccountOtpInformation {

    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    private String code = NanoIdUtils.randomNanoId();

    private String otpCode;

    @Builder.Default
    private Integer validateCount = 0;
}
