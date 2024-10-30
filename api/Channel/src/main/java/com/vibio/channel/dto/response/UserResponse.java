/*
 *  UserResponse
 *  @author: Minhhieuano
 *  @created 10/25/2024 10:06 PM
 * */


package com.vibio.channel.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String id;
    private String email;
    private String username;
    private String avatar;
}
