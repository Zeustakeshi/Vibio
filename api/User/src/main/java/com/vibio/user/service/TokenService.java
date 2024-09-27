/*
 *  TokenService
 *  @author: Minhhieuano
 *  @created 9/11/2024 10:16 PM
 * */

package com.vibio.user.service;

import com.vibio.user.domain.Token;
import com.vibio.user.dto.request.IntrospectTokenRequest;
import com.vibio.user.dto.request.RefreshTokenRequest;
import com.vibio.user.dto.response.TokenResponse;
import com.vibio.user.model.Account;
import java.util.Map;

public interface TokenService {

	TokenResponse generateTokenPair(Account account);

	TokenResponse refreshToken(RefreshTokenRequest request);

	Boolean introspectToken(IntrospectTokenRequest request);

	Token generateAccessToken(Account account);

	Token generateRefreshToken(String accessTokenId, Account account);

	Map<String, Object> getJwkSets();
}
