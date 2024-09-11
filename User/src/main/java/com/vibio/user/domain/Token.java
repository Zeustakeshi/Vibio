/*
 *  Token
 *  @author: Minhhieuano
 *  @created 9/9/2024 1:00 AM
 * */

package com.vibio.user.domain;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.vibio.user.common.enums.TokenType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class Token {
	@Setter(AccessLevel.PRIVATE)
	@Builder.Default
	private String id = NanoIdUtils.randomNanoId();

	private String value;
	private TokenType type;
	private LocalDateTime expiresIn;
}
