/*
 *  UpdatePlaylistRequest
 *  @author: Minhhieuano
 *  @created 10/17/2024 5:44 PM
 * */

package com.vibio.channel.dto.request;

import com.vibio.channel.common.enums.Visibility;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePlaylistRequest {
	@Size(max = 500, min = 5)
	private String name;

	@Size(max = 2000, min = 5)
	private String description;

	private Visibility visibility;
}
