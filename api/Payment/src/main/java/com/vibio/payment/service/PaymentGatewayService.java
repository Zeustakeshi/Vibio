/*
 *  PaymentGatewayService
 *  @author: Minhhieuano
 *  @created 10/24/2024 3:30 PM
 * */


package com.vibio.payment.service;

import com.vibio.payment.dto.response.MomoVerifyPaymentResponse;
import com.vibio.payment.entity.Payment;

public interface PaymentGatewayService {
    String createPayment(Payment payment, String returnUrl);

    MomoVerifyPaymentResponse verifyPayment(String paymentId);

}
