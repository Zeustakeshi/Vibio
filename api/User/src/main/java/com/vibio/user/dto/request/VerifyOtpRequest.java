/*
 *  VerifyOtpRequest
 *  @author: Minhhieuano
 *  @created 9/9/2024 12:58 AM
 * */

package com.vibio.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyOtpRequest {
	@NotNull @NotBlank
	private String code;

	@NotNull @NotBlank
	private String otp;
}
