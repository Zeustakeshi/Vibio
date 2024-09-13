/*
 *  GlobalExceptionHandler
 *  @author: Minhhieuano
 *  @created 9/8/2024 9:07 PM
 * */

package com.vibio.user.exception;

import com.vibio.user.dto.response.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse<?>> handleApiException(ApiException exception) {
		ApiResponse<?> response = ApiResponse.error(exception.getMessage());
		return new ResponseEntity<>(response, exception.getStatus());
	}

	@ExceptionHandler(TokenException.class)
	public ResponseEntity<ApiResponse<?>> handleException(TokenException exception) {
		ApiResponse<?> response = ApiResponse.error(exception.getMessage());
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleException(Exception exception) {
		ApiResponse<?> response = ApiResponse.error(exception.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		Map<String, Object> errors = new HashMap<>();

		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return new ResponseEntity<>(ApiResponse.error(errors), ex.getStatusCode());
	}
}
