/*
 *  MomoVerifyRequest
 *  @author: Minhhieuano
 *  @created 10/25/2024 12:32 AM
 * */


package com.vibio.payment.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MomoVerifyPaymentRequest {
    private String partnerCode;
    private String requestId;
    private String orderId;

    @Builder.Default
    private String lang = "vi";
    private String signature;
}
