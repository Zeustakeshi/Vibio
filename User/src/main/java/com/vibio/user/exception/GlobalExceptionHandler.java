/*
 *  GlobalExceptionHandler
 *  @author: Minhhieuano
 *  @created 9/8/2024 9:07 PM
 * */

package com.vibio.user.exception;

import com.vibio.user.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse<?>> handleApiException(ApiException exception) {
		ApiResponse<?> response = ApiResponse.success(exception.getMessage());
		return new ResponseEntity<>(response, exception.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleException(Exception exception) {
		ApiResponse<?> response = ApiResponse.success(exception.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
