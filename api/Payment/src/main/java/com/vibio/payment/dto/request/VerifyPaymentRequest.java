/*
 *  VerifyPaymentRequest
 *  @author: Minhhieuano
 *  @created 10/25/2024 11:35 AM
 * */


package com.vibio.payment.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyPaymentRequest {

    @NotEmpty
    @NotNull
    private String paymentId;
}
