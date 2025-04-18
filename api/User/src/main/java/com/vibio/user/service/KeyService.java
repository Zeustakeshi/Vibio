/*
 *  KeyService
 *  @author: Minhhieuano
 *  @created 9/11/2024 10:45 PM
 * */

package com.vibio.user.service;

import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;

public interface KeyService {
	//	/**
	//	 * Retrieves the public key for access token verification.
	//	 *
	//	 * @return the RSAPublicKey for access tokens
	//	 */
	RSAPublicKey getAccessTokenPublicKey();

	/**
	 * Retrieves the private key for signing access tokens.
	 *
	 * @return the RSAPrivateKey for signing access tokens
	 */
	PrivateKey getAccessTokenPrivateKey();

	/**
	 * Retrieves the public key for refresh token verification.
	 *
	 * @return the RSAPublicKey for refresh tokens
	 */
	RSAPublicKey getRefreshTokenPublicKey();

	/**
	 * Retrieves the private key for signing refresh tokens.
	 *
	 * @return the RSAPrivateKey for signing refresh tokens
	 */
	PrivateKey getRefreshTokenPrivateKey();
}
