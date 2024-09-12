/*
 *  LoginRequest
 *  @author: Minhhieuano
 *  @created 9/12/2024 9:54 AM
 * */


package com.vibio.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String password;
}
