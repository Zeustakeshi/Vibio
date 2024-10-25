/*
 *  MembershipPaymentRequest
 *  @author: Minhhieuano
 *  @created 10/25/2024 11:31 AM
 * */


package com.vibio.payment.dto.request;

import com.vibio.payment.common.enums.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
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
