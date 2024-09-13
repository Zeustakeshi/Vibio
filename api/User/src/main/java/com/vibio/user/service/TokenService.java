/*
 *  TokenService
 *  @author: Minhhieuano
 *  @created 9/11/2024 10:16 PM
 * */

package com.vibio.user.service;

import com.vibio.user.domain.Token;
import com.vibio.user.dto.response.TokenResponse;
import com.vibio.user.model.Account;

public interface TokenService {

	TokenResponse generateTokenPair(Account account);

	TokenResponse refreshToken(String refreshToken);

	Token generateAccessToken(Account account);

	Token generateRefreshToken(String accessTokenId, Account account);
}
