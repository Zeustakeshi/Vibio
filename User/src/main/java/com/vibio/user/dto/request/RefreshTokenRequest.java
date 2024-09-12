/*
 *  RefreshTokenRequest
 *  @author: Minhhieuano
 *  @created 9/12/2024 7:12 AM
 * */

package com.vibio.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenRequest {
	@NotNull @NotBlank
	private String token;
}
