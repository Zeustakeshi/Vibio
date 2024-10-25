/*
 *  MomoClient
 *  @author: Minhhieuano
 *  @created 10/24/2024 4:29 PM
 * */

package com.vibio.payment.client;


import com.vibio.payment.dto.request.MomoPaymentRequest;
import com.vibio.payment.dto.request.MomoVerifyPaymentRequest;
import com.vibio.payment.dto.response.MomoPaymentResponse;
import com.vibio.payment.dto.response.MomoVerifyPaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "momo-gateway", url = "${payment-gateway.momo.endpoint}")
public interface MomoClient {

    @PostMapping("/create")
    MomoPaymentResponse getPaymentUrl(@RequestBody MomoPaymentRequest request);


    @PostMapping("/query")
    MomoVerifyPaymentResponse verifyPayment(@RequestBody MomoVerifyPaymentRequest request);
}
