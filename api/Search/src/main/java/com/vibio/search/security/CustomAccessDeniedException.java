/*
 *  CustomAccessdenineException
 *  @author: Minhhieuano
 *  @created 9/15/2024 2:41 PM
 * */

package com.vibio.search.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
public class CustomAccessDeniedException implements AccessDeniedHandler {
    private HandlerExceptionResolver resolver;

    public CustomAccessDeniedException(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.resolver = exceptionResolver;
    }

    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        log.error(accessDeniedException.getMessage());
        resolver.resolveException(request, response, null, accessDeniedException);
    }
}
