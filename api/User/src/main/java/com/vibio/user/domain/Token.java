/*
 *  Token
 *  @author: Minhhieuano
 *  @created 9/9/2024 1:00 AM
 * */

package com.vibio.user.domain;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.vibio.user.common.enums.TokenType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Token {
	@Builder.Default
	private String id = NanoIdUtils.randomNanoId();

	private String value;
	private TokenType type;
	private Long expiresIn;
}
