package com.dev.bankingApp.banking.mapper;

import com.dev.bankingApp.banking.dto.AccountDTO;
import com.dev.bankingApp.banking.entity.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountDTO accountDTO){
        Account account = new Account(
                accountDTO.id(),
                accountDTO.accHolderName(),
                accountDTO.accBalance()
        );
        return account;
    }

    public static AccountDTO mapToAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO(
                account.getId(),
                account.getAccHolderName(),
                account.getAccBalance()
        );
        return accountDTO;
    }
}
