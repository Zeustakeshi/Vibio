/*
 *  SubscriptionResponse
 *  @author: Minhhieuano
 *  @created 10/10/2024 8:30 AM
 * */


package com.vibio.channel.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionResponse {
    private String channelId;
    private String userId;
    private boolean subscribed;
}
