/*
 *  UserResponse
 *  @author: Minhhieuano
 *  @created 10/6/2024 9:11 AM
 * */

package com.vibio.video.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
	private String id;
	private String avatar;
	private String username;
}
