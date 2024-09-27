/*
 *  AccountService
 *  @author: Minhhieuano
 *  @created 9/13/2024 1:30 PM
 * */

package com.vibio.user.service;

import com.vibio.user.dto.response.AccountResponse;
import com.vibio.user.model.Account;

public interface AccountService {
	AccountResponse getAccountInfo(Account account);
}
