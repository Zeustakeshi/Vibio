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
import com.vibio.user.event.eventModel.NewChannelEvent;
import com.vibio.user.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountConfirmation createAccountRequestToAccountConfirmation(CreateAccountRequest request);

    Account accountConfirmationToAccount(AccountConfirmation accountConfirmation);

    AccountMFA accountToAccountMFAConfirmation(Account account);

    @Mapping(target = "channel", ignore = true)
    AccountResponse accountToAccountResponse(Account account);

    @Mapping(source = "id", target = "accountId")
    @Mapping(source = "username", target = "name")
    @Mapping(source = "avatar", target = "defaultAvatar")
    @Mapping(source = "email", target = "defaultEmail")
    @Mapping(target = "id", ignore = true)
    NewChannelEvent accountToNewChannelEvent(Account account);
}
