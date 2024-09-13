/*
 *  InvalidTokenException
 *  @author: Minhhieuano
 *  @created 9/12/2024 8:07 AM
 * */

package com.vibio.user.exception;

public class InvalidTokenException extends TokenException {
	public InvalidTokenException(String message) {
		super(message);
	}
}
