/*
 *  OTP
 *  @author: Minhhieuano
 *  @created 9/8/2024 11:34 PM
 * */

package com.vibio.user.domain;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class OTP {
	@Builder.Default
	@Setter(AccessLevel.PRIVATE)
	private String code = NanoIdUtils.randomNanoId();

	private String value;
	private Long expiresIn;
}
