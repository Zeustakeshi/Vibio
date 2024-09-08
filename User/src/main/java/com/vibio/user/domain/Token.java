/*
 *  Token
 *  @author: Minhhieuano
 *  @created 9/9/2024 1:00 AM
 * */

package com.vibio.user.domain;

import com.vibio.user.common.enums.TokenType;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Token {
	private String value;
	private TokenType type;
	private LocalDateTime expiresIn;
}
