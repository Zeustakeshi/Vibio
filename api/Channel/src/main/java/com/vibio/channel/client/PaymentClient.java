/*
 *  VideoClient
 *  @author: Minhhieuano
 *  @created 10/17/2024 6:57 PM
 * */

package com.vibio.channel.client;

import com.vibio.channel.dto.request.MembershipPaymentRequest;
import com.vibio.channel.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${services-url.payment}")
public interface PaymentClient {
    @PostMapping("/internal/member")
    ApiResponse<String> createPayment(
            @RequestBody @Valid MembershipPaymentRequest request
    );
}
