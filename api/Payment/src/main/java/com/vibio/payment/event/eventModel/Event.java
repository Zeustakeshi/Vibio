/*
 *  Event
 *  @author: Minhhieuano
 *  @created 9/27/2024 9:19 PM
 * */

package com.vibio.payment.event.eventModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Event {
    private String id;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
