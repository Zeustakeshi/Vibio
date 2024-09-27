/*
 *  introspectTokenRequest
 *  @author: Minhhieuano
 *  @created 9/14/2024 12:53 AM
 * */

package com.vibio.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IntrospectTokenRequest {
	@NotNull @NotBlank
	private String token;
}
