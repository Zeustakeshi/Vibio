/*
 *  CustomAuthenticationEntryPoint
 *  @author: Minhhieuano
 *  @created 9/15/2024 2:39 PM
 * */

package com.vibio.user.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final HandlerExceptionResolver resolver;

	public CustomAuthenticationEntryPoint(
			@Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
		this.resolver = handlerExceptionResolver;
	}

	@Override
	public void commence(
			HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		resolver.resolveException(request, response, null, authException);
	}
}
