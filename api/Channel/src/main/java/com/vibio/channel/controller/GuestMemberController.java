/*
 *  GuestMemberController
 *  @author: Minhhieuano
 *  @created 10/25/2024 10:05 PM
 * */


package com.vibio.channel.controller;

import com.vibio.channel.dto.response.ApiResponse;
import com.vibio.channel.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guest/{channelId}/members")
@RequiredArgsConstructor
public class GuestMemberController {
    private final MemberService memberService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getAllMember(
            @PathVariable("channelId") String channelId,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ApiResponse.success(memberService.getAllChannelMember(channelId, page, limit));
    }
}
