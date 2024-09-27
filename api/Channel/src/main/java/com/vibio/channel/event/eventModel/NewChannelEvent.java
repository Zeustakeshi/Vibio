/*
 *  CreateChannelEvent
 *  @author: Minhhieuano
 *  @created 9/27/2024 9:18 PM
 * */

package com.vibio.channel.event.eventModel;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class NewChannelEvent extends Event {
	@Builder.Default
	private String id = "E_002";

	private String accountId;
	private String defaultAvatar;
	private String defaultEmail;
	private String name;
}
