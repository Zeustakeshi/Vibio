/*
 *  CustomFeignErrorDecoder
 *  @author: Minhhieuano
 *  @created 9/29/2024 9:52 PM
 * */

package com.vibio.video.client.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibio.video.dto.response.ApiResponse;
import com.vibio.video.exception.*;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomFeignErrorDecoder implements ErrorDecoder {

	private final ObjectMapper objectMapper;

	@Override
	public Exception decode(String s, Response response) {

		try {
			ApiResponse<?> errorResponse =
					objectMapper.readValue(response.body().asInputStream(), ApiResponse.class);

			String errorMessage = errorResponse.getErrors().toString();

			return convertStatusToApiException(response.status(), errorMessage);

		} catch (IOException e) {
			log.error(e.getMessage());
			return new InternalServerErrorException(e.getMessage());
		}
	}

	private ApiException convertStatusToApiException(int status, String message) {
		switch (status) {
			case 404 -> throw new NotfoundException(message);
			case 403 -> throw new ForbiddenException(message);
			case 409 -> throw new ConflictException(message);
			default -> throw new InternalServerErrorException(message);
		}
	}
}
