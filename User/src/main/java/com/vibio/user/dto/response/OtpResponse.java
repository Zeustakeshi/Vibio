/*
 *  OtpResponse
 *  @author: Minhhieuano
 *  @created 9/12/2024 10:01 AM
 * */

package com.vibio.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OtpResponse {
	private String code;
}
