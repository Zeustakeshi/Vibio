/*
 *  InternalPaymentController
 *  @author: Minhhieuano
 *  @created 10/25/2024 1:00 PM
 * */


package com.vibio.payment.controller;

import com.vibio.payment.dto.request.MembershipPaymentRequest;
import com.vibio.payment.dto.response.ApiResponse;
import com.vibio.payment.service.MembershipPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/member")
@RequiredArgsConstructor
public class InternalPaymentMembershipController {
    private final MembershipPaymentService membershipPaymentService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> createPayment(
            @RequestBody @Valid MembershipPaymentRequest request
    ) {
        return ApiResponse.success(membershipPaymentService.createPayment(request));
    }
}
