/*
 *  InternalMemberService
 *  @author: Minhhieuano
 *  @created 10/26/2024 12:43 AM
 * */


package com.vibio.channel.service;

import com.vibio.channel.dto.request.CheckMembershipRequest;

import java.util.List;

public interface InternalMemberService {
    List<String> checkMembershipStatus(CheckMembershipRequest request);
}
