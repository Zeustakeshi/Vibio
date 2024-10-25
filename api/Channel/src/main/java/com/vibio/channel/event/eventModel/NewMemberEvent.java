/*
 *  NewMemberEvent
 *  @author: Minhhieuano
 *  @created 10/25/2024 11:48 AM
 * */

package com.vibio.channel.event.eventModel;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class NewMemberEvent extends Event {
    private final String id = "E_005";
    private String channelId;
    private String memberId;
    private String paymentId;
}
