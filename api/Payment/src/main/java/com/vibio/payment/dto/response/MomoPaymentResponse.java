/*
 *  MomoPaymentResponse
 *  @author: Minhhieuano
 *  @created 10/25/2024 12:22 AM
 * */


package com.vibio.payment.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MomoPaymentResponse {
    private String payUrl;
    private String shortLink;
}
