/*
 *  MemberController
 *  @author: Minhhieuano
 *  @created 10/25/2024 1:06 PM
 * */


package com.vibio.channel.controller;

import com.vibio.channel.dto.common.AuthenticatedUser;
import com.vibio.channel.dto.request.JoinMemberRequest;
import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("{channelId}/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> joinMember(
            @RequestBody @Valid JoinMemberRequest request,
            @PathVariable("channelId") String channelId,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return ApiResponse.success(memberService.joinChannelMember(channelId, user.getId(), request));
    }

}
