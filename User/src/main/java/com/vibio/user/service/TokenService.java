/*
 *  TokenService
 *  @author: Minhhieuano
 *  @created 9/11/2024 10:16 PM
 * */

package com.vibio.user.service;

import com.vibio.user.domain.Token;
import org.springframework.security.core.Authentication;

public interface TokenService {
    Token generateAccessToken(Authentication authentication);

    Token generateRefreshToken(Authentication authentication);
    
}
