/*
 *  InternalAccountService
 *  @author: Minhhieuano
 *  @created 10/6/2024 10:12 AM
 * */


package com.vibio.user.service;

import com.vibio.user.dto.response.AccountResponse;

public interface InternalAccountService {
    AccountResponse getAccountInfo(String accountId);
}
