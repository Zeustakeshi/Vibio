/*
 *  PaymentController
 *  @author: Minhhieuano
 *  @created 10/24/2024 3:20 PM
 * */


package com.vibio.payment.controller;

import com.vibio.payment.dto.request.VerifyPaymentRequest;
import com.vibio.payment.dto.response.ApiResponse;
import com.vibio.payment.service.MembershipPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class PaymentMembershipController {

    private final MembershipPaymentService membershipPaymentService;


    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> verifyPayment(
            @RequestBody @Valid VerifyPaymentRequest request
    ) {
        return ApiResponse.success(membershipPaymentService.verifyPayment(request));
    }
}
