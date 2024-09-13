/*
 *  GlobalGatewayExceptionHandler
 *  @author: Minhhieuano
 *  @created 9/14/2024 12:01 AM
 * */

package com.vibio.gateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibio.gateway.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GlobalGatewayExceptionHandler implements ErrorWebExceptionHandler {

	private final ObjectMapper objectMapper;

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

		HttpStatus status;
		String message;
		if (ex instanceof ApiException) {
			status = ((ApiException) ex).getStatus();
			message = ex.getMessage();
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = "An unexpected error occurred.";
		}

		exchange.getResponse().setStatusCode(status);
		exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		byte[] bytes = convertObjectToJsonBytes(ApiResponse.error(message));

		return exchange.getResponse()
				.writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
	}

	private byte[] convertObjectToJsonBytes(Object object) {
		try {
			return objectMapper.writeValueAsBytes(object);
		} catch (Exception e) {
			throw new RuntimeException("Error converting object to JSON", e);
		}
	}
}
