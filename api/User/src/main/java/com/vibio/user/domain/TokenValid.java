/*
 *  TokenValid
 *  @author: Minhhieuano
 *  @created 9/12/2024 7:39 AM
 * */

package com.vibio.user.domain;

import com.vibio.user.common.enums.TokenType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenValid {
	private String tokenId;
	private TokenType type;
}
