/*
 *  MomoPaymentRequest
 *  @author: Minhhieuano
 *  @created 10/24/2024 4:31 PM
 * */


package com.vibio.payment.dto.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MomoPaymentRequest {
    private String partnerCode;
    private String partnerName;
    private String storeId;
    private String requestType;
    private String ipnUrl;
    private String redirectUrl;
    private String orderId;
    private Long amount;
    private String lang;
    private boolean autoCapture;
    private String orderInfo;
    private String requestId;
    private String extraData;
    private String signature;
}
