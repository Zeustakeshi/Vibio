/*
 *  ResendOtpRequest
 *  @author: Minhhieuano
 *  @created 9/9/2024 12:31 AM
 * */

package com.vibio.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResendOtpRequest {
	@NotNull @NotBlank
	private String code;
}
