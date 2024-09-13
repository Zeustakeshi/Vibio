/*
 *  UserResponse
 *  @author: Minhhieuano
 *  @created 9/13/2024 1:19 PM
 * */

package com.vibio.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {
	private String id;
	private String email;
	private String username;
	private String avatar;
}
