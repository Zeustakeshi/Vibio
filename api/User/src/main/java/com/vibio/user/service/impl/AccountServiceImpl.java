/*
 *  AccountService
 *  @author: Minhhieuano
 *  @created 9/13/2024 1:29 PM
 * */

package com.vibio.user.service.impl;

import com.vibio.user.client.ChannelClient;
import com.vibio.user.dto.response.AccountResponse;
import com.vibio.user.dto.response.ApiResponse;
import com.vibio.user.dto.response.ChannelResponse;
import com.vibio.user.mapper.AccountMapper;
import com.vibio.user.model.Account;
import com.vibio.user.repository.AccountRepository;
import com.vibio.user.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ChannelClient channelClient;

    @Override
    public AccountResponse getAccountInfo(Account account) {
        AccountResponse accountResponse = accountMapper.accountToAccountResponse(account);
        ApiResponse<ChannelResponse> channelResponse = channelClient.getChannelByAccountId(account.getId());
        accountResponse.setChannel(channelResponse.getData());
        return accountResponse;
    }
}
