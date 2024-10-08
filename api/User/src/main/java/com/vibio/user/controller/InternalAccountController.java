/*
 *  InternalAccountController
 *  @author: Minhhieuano
 *  @created 10/6/2024 10:08 AM
 * */


package com.vibio.user.controller;

import com.vibio.user.dto.response.ApiResponse;
import com.vibio.user.service.InternalAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/account")
@RequiredArgsConstructor
public class InternalAccountController {
    private final InternalAccountService accountService;

    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getAccountByAccountId(@PathVariable("accountId") String accountId) {
        return ApiResponse.success(accountService.getAccountInfo(accountId));
    }

}
