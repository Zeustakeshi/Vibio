/*
 *  MembershipPaymentRequest
 *  @author: Minhhieuano
 *  @created 10/25/2024 11:31 AM
 * */


package com.vibio.channel.dto.request;

import com.vibio.channel.common.enums.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MembershipPaymentRequest {
    @NotNull
    PaymentMethod method;

    @NotEmpty
    private String channelId;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String redirectUrl;

}
