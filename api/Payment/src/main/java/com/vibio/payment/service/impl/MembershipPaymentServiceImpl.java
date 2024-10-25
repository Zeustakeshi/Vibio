/*
 *  PaymentMemberServiceImpl
 *  @author: Minhhieuano
 *  @created 10/24/2024 3:22 PM
 * */


package com.vibio.payment.service.impl;

import com.vibio.payment.common.enums.PaymentMethod;
import com.vibio.payment.common.enums.PaymentStatus;
import com.vibio.payment.common.enums.TransactionType;
import com.vibio.payment.common.properties.UtilProperties;
import com.vibio.payment.dto.request.MembershipPaymentRequest;
import com.vibio.payment.dto.request.VerifyPaymentRequest;
import com.vibio.payment.dto.response.MomoVerifyPaymentResponse;
import com.vibio.payment.entity.Payment;
import com.vibio.payment.event.eventModel.NewMemberEvent;
import com.vibio.payment.event.producer.ChannelEventProducer;
import com.vibio.payment.exception.NotfoundException;
import com.vibio.payment.repository.PaymentRepository;
import com.vibio.payment.service.MembershipPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembershipPaymentServiceImpl implements MembershipPaymentService {

    private final MomoGatewayService momoGatewayService;
    private final UtilProperties utilProperties;
    private final PaymentRepository paymentRepository;
    private final ChannelEventProducer channelEventProducer;

    @Override
    public String createPayment(MembershipPaymentRequest request) {

        if (request.getMethod().equals(PaymentMethod.VN_PAY)) {
            throw new NotfoundException("Payment by vnPay will be launched soon!");
        }

        Payment payment = paymentRepository.save(Payment
                .builder()
                .amount(utilProperties.membershipFee())
                .payInfo("Membership Payments")
                .method(PaymentMethod.MOMO)
                .referenceId(request.getChannelId())
                .userId(request.getUserId())
                .transactionType(TransactionType.SUBSCRIPTION)
                .build());

        return momoGatewayService.createPayment(payment, request.getRedirectUrl());

    }

    @Override
    public Boolean verifyPayment(VerifyPaymentRequest request) {

        String paymentId = request.getPaymentId();

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotfoundException("Payment not found"));

        MomoVerifyPaymentResponse paymentResponse = momoGatewayService.verifyPayment(paymentId);

        if (paymentResponse.getResultCode() != 0 || !payment.getCreatedAt().isEqual(payment.getUpdatedAt())) {
            return false;
        }

        payment.setTransId(paymentResponse.getTransId());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPayType(paymentResponse.getPayType());

        Payment updatedPayment = paymentRepository.save(payment);

        // send create member event to channel service
        NewMemberEvent newMemberEvent = NewMemberEvent.builder()
                .channelId(updatedPayment.getReferenceId())
                .memberId(updatedPayment.getUserId())
                .paymentId(updatedPayment.getId())
                .build();

        channelEventProducer.newMember(newMemberEvent);

        return true;
    }
}
