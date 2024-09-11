/*
 *  TokenServiceImpl
 *  @author: Minhhieuano
 *  @created 9/11/2024 11:00 PM
 * */

package com.vibio.user.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.vibio.user.common.enums.TokenType;
import com.vibio.user.common.properties.AccessTokenProperties;
import com.vibio.user.common.properties.RefreshTokenProperties;
import com.vibio.user.domain.Token;
import com.vibio.user.dto.response.TokenResponse;
import com.vibio.user.model.Account;
import com.vibio.user.service.TokenService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

	private final JwtEncoder accessTokenEncoder;
	private final JwtEncoder refreshTokenEncoder;
	private final JwtDecoder refreshTokenDecoder;
	private final AccessTokenProperties accessTokenProperties;
	private final RefreshTokenProperties refreshTokenProperties;

	public TokenServiceImpl(
			@Qualifier("accessTokenEncoder") JwtEncoder accessTokenEncoder,
			@Qualifier("refreshTokenEncoder") JwtEncoder refreshTokenEncoder,
			@Qualifier("refreshTokenDecoder") JwtDecoder accessTokenDecoder,
			AccessTokenProperties accessTokenProperties,
			RefreshTokenProperties refreshTokenProperties) {
		this.accessTokenEncoder = accessTokenEncoder;
		this.refreshTokenEncoder = refreshTokenEncoder;
		this.refreshTokenDecoder = accessTokenDecoder;
		this.accessTokenProperties = accessTokenProperties;
		this.refreshTokenProperties = refreshTokenProperties;
	}

	@Override
	public TokenResponse generateTokenPair(Account account) {
		Token accessToken = generateAccessToken(account);
		Token refreshToken = generateRefreshToken(accessToken);
		return TokenResponse.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

	@Override
	public Token generateAccessToken(Account account) {
		// Get the current time
		LocalDateTime now = LocalDateTime.now();

		// Calculate the expiration time for the access token
		LocalDateTime expireTime = now.plusHours(accessTokenProperties.expireIn());

		// Build the JWT claims set for the access token
		JwtClaimsSet claimsSet = JwtClaimsSet.builder()
				.subject(account.getId())
				.claim("email", account.getEmail())
				.claim("scope", account.getAuthorities()) // Add account's authorities/roles
				.claim("type", TokenType.ACCESS_TOKEN)
				.expiresAt(expireTime.toInstant(ZoneOffset.UTC))
				.build();

		// Encode the JWT token from the claims
		String token =
				accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

		return Token.builder()
				.type(TokenType.ACCESS_TOKEN)
				.value(token)
				.expiresIn(expireTime)
				.build();
	}

	@Override
	public Token generateRefreshToken(Token accessToken) {
		// Get the current time
		LocalDateTime now = LocalDateTime.now();

		// Calculate the expiration time for the refresh token
		LocalDateTime expireTime = now.plusHours(refreshTokenProperties.expireIn());

		// Build the JWT claims set for the refresh token
		JwtClaimsSet claimsSet = JwtClaimsSet.builder()
				.subject(NanoIdUtils.randomNanoId())
				.claim("token_id", accessToken.getId()) // Link the refresh token to the access token
				.claim("type", TokenType.REFRESH_TOKEN)
				.expiresAt(expireTime.toInstant(ZoneOffset.UTC))
				.build();

		// Encode the JWT token from the claims
		String token =
				refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

		return Token.builder()
				.type(TokenType.REFRESH_TOKEN)
				.value(token)
				.expiresIn(expireTime)
				.build();
	}
}
