/*
 *  PaymentMemberService
 *  @author: Minhhieuano
 *  @created 10/24/2024 3:21 PM
 * */

package com.vibio.payment.service;

import com.vibio.payment.dto.request.MembershipPaymentRequest;
import com.vibio.payment.dto.request.VerifyPaymentRequest;

public interface MembershipPaymentService {
    String createPayment(MembershipPaymentRequest request);

    Boolean verifyPayment(VerifyPaymentRequest request);
}
