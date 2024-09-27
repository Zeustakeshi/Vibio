/*
 *  TokenServiceImpl
 *  @author: Minhhieuano
 *  @created 9/11/2024 11:00 PM
 * */

package com.vibio.user.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.google.gson.Gson;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.vibio.user.common.enums.TokenType;
import com.vibio.user.common.properties.AccessTokenProperties;
import com.vibio.user.common.properties.RefreshTokenProperties;
import com.vibio.user.domain.Token;
import com.vibio.user.domain.TokenValid;
import com.vibio.user.dto.request.IntrospectTokenRequest;
import com.vibio.user.dto.request.RefreshTokenRequest;
import com.vibio.user.dto.response.TokenResponse;
import com.vibio.user.exception.InvalidTokenException;
import com.vibio.user.exception.NotfoundException;
import com.vibio.user.model.Account;
import com.vibio.user.repository.AccountRepository;
import com.vibio.user.service.KeyService;
import com.vibio.user.service.TokenService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class TokenServiceImpl implements TokenService {

	private static final String TOKEN_ID_KEY = "token_id";
	private static final String TOKEN_TYPE_KEY = "type";
	private static final String ACCESS_TOKEN_ID_KEY = "access_token_id";

	private final Jedis jedis;
	private final Gson gson;
	private final AccountRepository accountRepository;
	private final JwtEncoder accessTokenEncoder;
	private final JwtEncoder refreshTokenEncoder;
	private final JwtDecoder refreshTokenDecoder;
	private final JwtDecoder accessTokenDecoder;
	private final AccessTokenProperties accessTokenProperties;
	private final RefreshTokenProperties refreshTokenProperties;
	private final KeyService keyService;

	public TokenServiceImpl(
			Jedis jedis,
			Gson gson,
			KeyService keyService,
			AccountRepository accountRepository,
			AccessTokenProperties accessTokenProperties,
			RefreshTokenProperties refreshTokenProperties,
			@Qualifier("accessTokenEncoder") JwtEncoder accessTokenEncoder,
			@Qualifier("refreshTokenEncoder") JwtEncoder refreshTokenEncoder,
			@Qualifier("refreshTokenDecoder") JwtDecoder refreshTokenDecoder,
			@Qualifier("accessTokenDecoder") JwtDecoder accessTokenDecoder) {
		this.accessTokenEncoder = accessTokenEncoder;
		this.refreshTokenEncoder = refreshTokenEncoder;
		this.refreshTokenDecoder = refreshTokenDecoder;
		this.accessTokenDecoder = accessTokenDecoder;
		this.accessTokenProperties = accessTokenProperties;
		this.refreshTokenProperties = refreshTokenProperties;
		this.accountRepository = accountRepository;
		this.keyService = keyService;
		this.jedis = jedis;
		this.gson = gson;
	}

	@Override
	public TokenResponse generateTokenPair(Account account) {
		Token accessToken = generateAccessToken(account);
		Token refreshToken = generateRefreshToken(accessToken.getId(), account);
		return TokenResponse.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

	@Override
	public TokenResponse refreshToken(RefreshTokenRequest request) {
		// decode and validate refresh token
		Jwt jwt = decodeToken(request.getToken(), TokenType.REFRESH_TOKEN);

		// extract token claims
		String accessTokenId = jwt.getClaim(ACCESS_TOKEN_ID_KEY);
		String refreshTokenId = jwt.getClaim(TOKEN_ID_KEY);
		String accountId = jwt.getSubject();

		// delete existed tokens
		jedis.del(createTokenValidKey(accessTokenId, accountId));
		jedis.del(createTokenValidKey(refreshTokenId, accountId));

		Account account = accountRepository
				.findById(jwt.getSubject())
				.orElseThrow(() -> new NotfoundException("Account with id " + accountId + " not found"));

		// generate new tokens
		return generateTokenPair(account);
	}

	@Override
	public Token generateAccessToken(Account account) {
		// Get the current time
		LocalDateTime now = LocalDateTime.now();

		// Calculate the expiration time for the access token
		LocalDateTime expireTime = now.plusHours(accessTokenProperties.expireIn());

		// Create token id
		String tokenId = NanoIdUtils.randomNanoId();

		// Build the JWT claims set for the access token
		JwtClaimsSet claimsSet = JwtClaimsSet.builder()
				.subject(account.getId())
				.claim("email", account.getEmail())
				.claim("avatar", account.getAvatar())
				.claim("scope", account.getAuthorities()) // Add account's authorities/roles
				.claim(TOKEN_TYPE_KEY, TokenType.ACCESS_TOKEN)
				.claim(TOKEN_ID_KEY, tokenId)
				.expiresAt(expireTime.toInstant(ZoneOffset.UTC))
				.build();

		// Encode the JWT token from the claims
		String token =
				accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

		// Build valid token
		TokenValid tokenValid = TokenValid.builder()
				.tokenId(tokenId)
				.type(TokenType.ACCESS_TOKEN)
				.build();

		// Save valid token to cache
		jedis.setex(
				createTokenValidKey(tokenId, claimsSet.getSubject()),
				TimeUnit.HOURS.toSeconds(accessTokenProperties.expireIn()),
				gson.toJson(tokenValid));

		return Token.builder()
				.id(tokenId)
				.type(TokenType.ACCESS_TOKEN)
				.value(token)
				.expiresIn(expireTime.atZone(ZoneId.systemDefault()).toEpochSecond())
				.build();
	}

	@Override
	public Token generateRefreshToken(String accessTokenId, Account account) {
		// Get the current time
		LocalDateTime now = LocalDateTime.now();

		// Calculate the expiration time for the refresh token
		LocalDateTime expireTime = now.plusHours(refreshTokenProperties.expireIn());

		// create token id
		String tokenId = NanoIdUtils.randomNanoId();

		// Build the JWT claims set for the refresh token
		JwtClaimsSet claimsSet = JwtClaimsSet.builder()
				.subject(account.getId())
				.claim(ACCESS_TOKEN_ID_KEY, accessTokenId)
				.claim(TOKEN_ID_KEY, tokenId)
				.claim(TOKEN_TYPE_KEY, TokenType.REFRESH_TOKEN)
				.expiresAt(expireTime.toInstant(ZoneOffset.UTC))
				.build();

		// Encode the JWT token from the claims
		String token =
				refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

		// Build valid token
		TokenValid tokenValid = TokenValid.builder()
				.tokenId(tokenId)
				.type(TokenType.REFRESH_TOKEN)
				.build();

		// Save valid token to cache
		jedis.setex(
				createTokenValidKey(tokenId, claimsSet.getSubject()),
				TimeUnit.HOURS.toSeconds(refreshTokenProperties.expireIn()),
				gson.toJson(tokenValid));

		return Token.builder()
				.type(TokenType.REFRESH_TOKEN)
				.value(token)
				.expiresIn(expireTime.atZone(ZoneId.systemDefault()).toEpochSecond())
				.build();
	}

	@Override
	public Boolean introspectToken(IntrospectTokenRequest request) {
		try {
			Jwt jwt = accessTokenDecoder.decode(request.getToken());
			validateAccessToken(jwt);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public Map<String, Object> getJwkSets() {
		RSAKey rsaKey = new RSAKey.Builder(keyService.getAccessTokenPublicKey())
				.privateKey(keyService.getAccessTokenPrivateKey())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return jwkSet.toJSONObject();
	}

	private Jwt validateRefreshToken(Jwt jwt) {
		// get token id
		String tokenId = jwt.getClaim(TOKEN_ID_KEY);

		// validate token form cache
		Optional.ofNullable(jedis.get(createTokenValidKey(tokenId, jwt.getSubject())))
				.orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));

		// get token type
		String tokenType = jwt.getClaim(TOKEN_TYPE_KEY);

		// validate token type
		if (!tokenType.equals(TokenType.REFRESH_TOKEN.toString())) {
			throw new InvalidTokenException("Invalid token type");
		}

		return jwt;
	}

	private Jwt validateAccessToken(Jwt jwt) {
		// get token id
		String tokenId = jwt.getClaim(TOKEN_ID_KEY);

		// validate token form cache
		Optional.ofNullable(jedis.get(createTokenValidKey(tokenId, jwt.getSubject())))
				.orElseThrow(() -> new InvalidTokenException("Invalid access token"));

		// get token type
		String tokenType = jwt.getClaim(TOKEN_TYPE_KEY);

		// validate token type
		if (!tokenType.equals(TokenType.ACCESS_TOKEN.toString())) {
			throw new InvalidTokenException("Invalid token type");
		}

		return jwt;
	}

	private Jwt decodeToken(String token, TokenType type) {
		if (type.equals(TokenType.ACCESS_TOKEN)) {
			return validateAccessToken(accessTokenDecoder.decode(token));
		} else {
			return validateRefreshToken(refreshTokenDecoder.decode(token));
		}
	}

	private String createTokenValidKey(String tokenId, String tokenSub) {
		return tokenId + tokenSub;
	}
}
