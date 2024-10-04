/*
 *  TestFilter
 *  @author: Minhhieuano
 *  @created 10/4/2024 9:53 PM
 * */


package com.vibio.video.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("hello world");

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);


        filterChain.doFilter(request, response);
    }
}
