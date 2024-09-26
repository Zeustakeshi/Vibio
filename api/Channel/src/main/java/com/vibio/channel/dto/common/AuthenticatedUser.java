/*
 *  AuthenticatedUser
 *  @author: Minhhieuano
 *  @created 9/26/2024 11:38 PM
 * */


package com.vibio.channel.dto.common;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticatedUser {
    private String id;
    private String email;


}
