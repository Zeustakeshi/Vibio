/*
 *  ChannelResponse
 *  @author: Minhhieuano
 *  @created 10/9/2024 12:22 AM
 * */

package com.vibio.channel.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelResponse {
	private String id;
	private String thumbnail;
	private String name;
	private boolean isSubscribed;
	private boolean isMember;
}
