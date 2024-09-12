/*
 *  TokenException
 *  @author: Minhhieuano
 *  @created 9/12/2024 8:04 AM
 * */

package com.vibio.user.exception;

public abstract class TokenException extends RuntimeException {
	public TokenException(String message) {
		super(message);
	}
}
