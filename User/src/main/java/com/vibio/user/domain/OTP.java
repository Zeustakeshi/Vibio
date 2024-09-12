/*
 *  OTP
 *  @author: Minhhieuano
 *  @created 9/8/2024 11:34 PM
 * */

package com.vibio.user.domain;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.vibio.user.common.enums.OTPType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class OTP implements Cloneable {
    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    private String code = NanoIdUtils.randomNanoId();
    private String value;
    private OTPType type;
    private Long expiresIn;

    @Override
    public OTP clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (OTP) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
