/*
 *  MomoGatewayService
 *  @author: Minhhieuano
 *  @created 10/24/2024 3:32 PM
 * */


package com.vibio.payment.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.vibio.payment.client.MomoClient;
import com.vibio.payment.common.properties.MomoProperties;
import com.vibio.payment.dto.request.MomoPaymentRequest;
import com.vibio.payment.dto.request.MomoVerifyPaymentRequest;
import com.vibio.payment.dto.response.MomoVerifyPaymentResponse;
import com.vibio.payment.entity.Payment;
import com.vibio.payment.service.PaymentGatewayService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import static com.vibio.payment.utils.HmacSHA256.hmacSHA256;

@Service
@RequiredArgsConstructor
public class MomoGatewayService implements PaymentGatewayService {

    private static final String IPN_URL = "https://callback.url/notify";
    private static final String REQUEST_TYPE = "payWithMethod";

    private final MomoProperties momoProperties;
    private final MomoClient momoClient;

    @Override
    @SneakyThrows
    public String createPayment(Payment payment, String returnUrl) {

        String requestId = payment.getId();
        String orderId = payment.getId();
        String extraData = "";

        String rawSignature = "accessKey=" + momoProperties.accessKey() +
                "&amount=" + payment.getAmount() +
                "&extraData=" + extraData +
                "&ipnUrl=" + IPN_URL +
                "&orderId=" + orderId +
                "&orderInfo=" + payment.getPayInfo() +
                "&partnerCode=" + momoProperties.partnerCode() +
                "&redirectUrl=" + returnUrl +
                "&requestId=" + requestId +
                "&requestType=" + REQUEST_TYPE;

        String signature = hmacSHA256(rawSignature, momoProperties.secretKey());

        MomoPaymentRequest paymentRequest = MomoPaymentRequest.builder()
                .partnerCode(momoProperties.partnerCode())
                .storeId(NanoIdUtils.randomNanoId())
                .requestType(REQUEST_TYPE)
                .ipnUrl(IPN_URL)
                .redirectUrl(returnUrl)
                .orderId(orderId)
                .amount(payment.getAmount())
                .lang("vi")
                .autoCapture(true)
                .orderInfo(payment.getPayInfo())
                .requestId(requestId)
                .extraData("")
                .signature(signature)
                .build();

        return momoClient.getPaymentUrl(paymentRequest).getShortLink();

    }


    @Override
    @SneakyThrows
    public MomoVerifyPaymentResponse verifyPayment(String paymentId) {

        String rawSignature = "accessKey=" + momoProperties.accessKey() +
                "&orderId=" + paymentId +
                "&partnerCode=" + momoProperties.partnerCode() +
                "&requestId=" + paymentId;

        String signature = hmacSHA256(rawSignature, momoProperties.secretKey());

        MomoVerifyPaymentResponse response = momoClient.verifyPayment(MomoVerifyPaymentRequest.builder()
                .orderId(paymentId)
                .partnerCode(momoProperties.partnerCode())
                .requestId(paymentId)
                .signature(signature)
                .build()
        );

        return response;

    }

}
