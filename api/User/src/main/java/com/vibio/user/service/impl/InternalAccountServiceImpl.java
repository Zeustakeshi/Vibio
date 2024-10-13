/*
 *  InternalAccountServiceImpl
 *  @author: Minhhieuano
 *  @created 10/6/2024 10:12 AM
 * */

package com.vibio.user.service.impl;

import com.vibio.user.dto.request.FindAccountsByIdsRequest;
import com.vibio.user.dto.response.AccountResponse;
import com.vibio.user.exception.NotfoundException;
import com.vibio.user.mapper.AccountMapper;
import com.vibio.user.model.Account;
import com.vibio.user.repository.AccountRepository;
import com.vibio.user.service.InternalAccountService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalAccountServiceImpl implements InternalAccountService {

	private final AccountRepository accountRepository;
	private final AccountMapper accountMapper;

	@Override
	public AccountResponse getAccountInfo(String accountId) {
		Account account =
				accountRepository.findById(accountId).orElseThrow(() -> new NotfoundException("Account not found"));
		return accountMapper.accountToAccountResponse(account);
	}

	@Override
	public List<AccountResponse> getAccountByIds(FindAccountsByIdsRequest request) {

		List<Account> accounts = accountRepository.findAllByIdIn(request.getIds());

		return accounts.stream().map(accountMapper::accountToAccountResponse).toList();
	}
}
