/*
 *  InternalMemberServiceImpl
 *  @author: Minhhieuano
 *  @created 10/26/2024 12:44 AM
 * */


package com.vibio.channel.service.impl;

import com.vibio.channel.dto.request.CheckMembershipRequest;
import com.vibio.channel.repository.MemberRepository;
import com.vibio.channel.service.InternalMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalMemberServiceImpl implements InternalMemberService {

    private final MemberRepository memberRepository;

    @Override
    public List<String> checkMembershipStatus(CheckMembershipRequest request) {
        return memberRepository.getExistingMembers(request.getChannelId(), request.getUserIds());
    }
}
