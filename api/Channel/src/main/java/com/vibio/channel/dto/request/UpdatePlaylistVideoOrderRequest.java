/*
 *  UpdatePlaylistVideoOrderRequest
 *  @author: Minhhieuano
 *  @created 10/17/2024 8:44 PM
 * */

package com.vibio.channel.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePlaylistVideoOrderRequest {

	@Min(1)
	@Max(100)
	@NotNull private Integer newOrder;

	@NotNull private String videoId;
}
