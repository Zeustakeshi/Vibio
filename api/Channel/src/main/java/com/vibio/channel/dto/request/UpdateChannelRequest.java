/*
 *  UpdateChannelRequest
 *  @author: Minhhieuano
 *  @created 9/28/2024 12:31 AM
 * */

package com.vibio.channel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateChannelRequest {

	@NotBlank
	@NotNull @Size(min = 5)
	private String name;

	@NotBlank
	@NotNull @Size(min = 10, max = 500)
	private String description;
}
