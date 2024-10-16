/*
 *  Event
 *  @author: Minhhieuano
 *  @created 9/27/2024 9:19 PM
 * */

package com.vibio.notification.event.eventModel;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Event {
	private String id;

	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();
}
