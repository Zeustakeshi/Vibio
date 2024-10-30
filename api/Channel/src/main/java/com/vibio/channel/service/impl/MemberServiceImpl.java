/*
 *  MemberServiceImpl
 *  @author: Minhhieuano
 *  @created 10/25/2024 12:57 AM
 * */

package com.vibio.channel.service.impl;

import com.vibio.channel.client.PaymentClient;
import com.vibio.channel.client.UserClient;
import com.vibio.channel.common.properties.MembershipProperties;
import com.vibio.channel.dto.request.FindAccountsByIdsRequest;
import com.vibio.channel.dto.request.JoinMemberRequest;
import com.vibio.channel.dto.request.MembershipPaymentRequest;
import com.vibio.channel.dto.response.*;
import com.vibio.channel.exception.ConflictException;
import com.vibio.channel.exception.ForbiddenException;
import com.vibio.channel.mapper.MemberMapper;
import com.vibio.channel.mapper.PageMapper;
import com.vibio.channel.model.Member;
import com.vibio.channel.repository.MemberRepository;
import com.vibio.channel.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MembershipProperties membershipProperties;
    private final PaymentClient paymentClient;
    private final UserClient userClient;
    private final PageMapper pageMapper;
    private final MemberMapper memberMapper;

    @Override
    public PageableResponse<UserResponse> getAllChannelMember(String channelId, int page, int limit) {

        PageRequest pageRequest = PageRequest.of(page, limit);

        Page<Member> members = memberRepository.getAllByChannelId(channelId, pageRequest);

        List<String> memberIds = members.map(Member::getAccountId).stream().toList();

        List<UserResponse> users = userClient.getUsersByIds(
                new FindAccountsByIdsRequest(memberIds)
        ).getData();

        return pageMapper.toPageableResponse(members.map(member -> users
                .stream()
                .filter(u -> u.getId().equals(member.getAccountId()))
                .findFirst()
                .orElse(null)));
    }

    @Override
    public MemberResponse getMemberInfo(String channelId, String accountId) {
        Member member = memberRepository.findByChannelIdAndAccountId(channelId, accountId)
                .orElseThrow(() -> new ForbiddenException("You're not a member of this channel."));
        return memberMapper.memberToMemberResponse(member);
    }

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
