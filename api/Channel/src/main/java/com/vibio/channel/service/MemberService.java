/*
 *  MemberService
 *  @author: Minhhieuano
 *  @created 10/25/2024 12:56 AM
 * */

package com.vibio.channel.service;

import com.vibio.channel.dto.request.JoinMemberRequest;
import com.vibio.channel.dto.response.JoinMemberResponse;

public interface MemberService {
    boolean isChannelMember(String channelId, String accountId);

    JoinMemberResponse joinChannelMember(String channelId, String accountId, JoinMemberRequest request);

    void createNewMember(String channelId, String accountId, String paymentId);
}
