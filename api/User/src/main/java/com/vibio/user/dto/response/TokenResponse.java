/*
 *  TokenResponse
 *  @author: Minhhieuano
 *  @created 9/9/2024 12:59 AM
 * */

package com.vibio.user.dto.response;

import com.vibio.user.domain.Token;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
	private Token accessToken;
	private Token refreshToken;
}
