/*
 *  MomoVerifyResponse
 *  @author: Minhhieuano
 *  @created 10/25/2024 12:31 AM
 * */


package com.vibio.payment.dto.response;

import lombok.Data;

@Data
public class MomoVerifyPaymentResponse {
    private String orderId;
    private String requestId;
    private String transId;
    private String payType;
    private Integer resultCode;
    private String message;
}
