/*
 *  InternalMemberController
 *  @author: Minhhieuano
 *  @created 10/25/2024 12:58 AM
 * */

package com.vibio.channel.controller;

import com.vibio.channel.dto.request.CheckMembershipRequest;
import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.service.InternalMemberService;
import com.vibio.channel.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/members")
@RequiredArgsConstructor
public class InternalMemberController {
    private final MemberService memberService;
    private final InternalMemberService internalMemberService;

    @GetMapping("/isMember")
    @ResponseStatus(HttpStatus.OK)
    ApiResponse<?> isChannelMember(
            @RequestParam("channelId") String channelId, @RequestParam("accountId") String accountId) {
        return ApiResponse.success(memberService.isChannelMember(channelId, accountId));
    }

    @PostMapping("check-membership")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> checkMembershipStatus(@RequestBody @Valid CheckMembershipRequest request) {
        return ApiResponse.success(internalMemberService.checkMembershipStatus(request));
    }

}
