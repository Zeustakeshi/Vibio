/*
 *  MemberServiceImpl
 *  @author: Minhhieuano
 *  @created 10/25/2024 12:57 AM
 * */

package com.vibio.channel.service.impl;

import com.vibio.channel.client.PaymentClient;
import com.vibio.channel.common.properties.MembershipProperties;
import com.vibio.channel.dto.request.JoinMemberRequest;
import com.vibio.channel.dto.request.MembershipPaymentRequest;
import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.dto.response.JoinMemberResponse;
import com.vibio.channel.exception.ConflictException;
import com.vibio.channel.model.Member;
import com.vibio.channel.repository.MemberRepository;
import com.vibio.channel.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MembershipProperties membershipProperties;
    private final PaymentClient paymentClient;

    @Override
    public boolean isChannelMember(String channelId, String accountId) {
        return memberRepository.existsByChannelIdAndAccountId(channelId, accountId);
    }

    @Override
    public JoinMemberResponse joinChannelMember(String channelId, String accountId, JoinMemberRequest request) {
        if (isChannelMember(channelId, accountId)) {
            throw new ConflictException("You already member of this channel.");
        }

        ApiResponse<String> newPayment = paymentClient.createPayment(
                MembershipPaymentRequest
                        .builder()
                        .channelId(channelId)
                        .method(request.getPaymentMethod())
                        .userId(accountId)
                        .redirectUrl(request.getRedirectUrl())
                        .build()
        );

        return new JoinMemberResponse(newPayment.getData());
    }

    @Override
    public void createNewMember(String channelId, String accountId, String paymentId) {
        if (isChannelMember(channelId, accountId)) return;

        memberRepository.save(Member.builder()
                .accountId(accountId)
                .channelId(channelId)
                .paymentId(paymentId)
                .expireDate(LocalDateTime.now().plusDays(membershipProperties.expiredIn()))
                .build());

        // TODO: send notification to member and channel
    }
}
