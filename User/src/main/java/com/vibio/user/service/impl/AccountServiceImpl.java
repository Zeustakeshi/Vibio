/*
 *  AccountService
 *  @author: Minhhieuano
 *  @created 9/13/2024 1:29 PM
 * */

package com.vibio.user.service.impl;

import com.vibio.user.repository.AccountRepository;
import com.vibio.user.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	private final AccountRepository accountRepository;
}
