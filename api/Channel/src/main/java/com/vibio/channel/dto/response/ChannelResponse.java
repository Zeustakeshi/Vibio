/*
 *  ChannelResponse
 *  @author: Minhhieuano
 *  @created 9/27/2024 11:52 PM
 * */

package com.vibio.channel.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelResponse {
	private String id;
	private String name;
	private String description;
	private String thumbnail;
	private String background;
	private String defaultEmail;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
