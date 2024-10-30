/*
 *  MemberService
 *  @author: Minhhieuano
 *  @created 10/25/2024 12:56 AM
 * */

package com.vibio.channel.service;

import com.vibio.channel.dto.request.JoinMemberRequest;
import com.vibio.channel.dto.response.JoinMemberResponse;
import com.vibio.channel.dto.response.MemberResponse;
import com.vibio.channel.dto.response.PageableResponse;
import com.vibio.channel.dto.response.UserResponse;

public interface MemberService {

    PageableResponse<UserResponse> getAllChannelMember(String channelId, int page, int limit);

    MemberResponse getMemberInfo(String channelId, String accountId);

    boolean isChannelMember(String channelId, String accountId);

    JoinMemberResponse joinChannelMember(String channelId, String accountId, JoinMemberRequest request);

    void createNewMember(String channelId, String accountId, String paymentId);
}
