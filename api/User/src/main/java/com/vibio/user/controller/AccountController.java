/*
 *  AccountController
 *  @author: Minhhieuano
 *  @created 9/13/2024 1:26 PM
 * */

package com.vibio.user.controller;

import com.vibio.user.dto.response.ApiResponse;
import com.vibio.user.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountServiceImpl accountService;

    @GetMapping("/test")
    public ApiResponse<?> test() {
        return ApiResponse.success("this is private route");
    }
}
