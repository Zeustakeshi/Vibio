/*
 *  JwtAuthenticationConverter
 *  @author: Minhhieuano
 *  @created 9/15/2024 2:46 PM
 * */

package com.vibio.search.security;

import com.vibio.search.dto.common.AuthenticatedUser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        AuthenticatedUser user = AuthenticatedUser.builder()
                .id(jwt.getSubject())
                .email(jwt.getClaim("email"))
                .build();
        return new UsernamePasswordAuthenticationToken(user, null, getAuthorities(jwt));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Jwt jwt) {
        List<Map<String, String>> scope = jwt.getClaim("scope");
        return scope.stream()
                .map(r -> new SimpleGrantedAuthority(r.get("role")))
                .toList();
    }
}