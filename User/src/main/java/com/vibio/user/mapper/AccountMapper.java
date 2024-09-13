/*
 *  AccountMapper
 *  @author: Minhhieuano
 *  @created 9/9/2024 12:11 AM
 * */

package com.vibio.user.mapper;

import com.vibio.user.domain.AccountConfirmation;
import com.vibio.user.domain.AccountMFA;
import com.vibio.user.dto.request.CreateAccountRequest;
import com.vibio.user.dto.response.AccountResponse;
import com.vibio.user.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
	AccountConfirmation createAccountRequestToAccountConfirmation(CreateAccountRequest request);

	Account accountConfirmationToAccount(AccountConfirmation accountConfirmation);

	AccountMFA accountToAccountMFAConfirmation(Account account);

	AccountResponse accountToAccountResponse(Account account);
}
