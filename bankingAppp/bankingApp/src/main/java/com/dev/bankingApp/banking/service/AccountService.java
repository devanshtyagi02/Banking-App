package com.dev.bankingApp.banking.service;

import com.dev.bankingApp.banking.dto.AccountDTO;
import com.dev.bankingApp.banking.dto.TransactionDTO;
import com.dev.bankingApp.banking.dto.TransferFundsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AccountService {

    AccountDTO createAccount(AccountDTO accountDto);

    AccountDTO getAccountById(Long id);

    AccountDTO deposite(Long id, double amount);

    AccountDTO credit(Long id, double amount);

    List<AccountDTO> getAllAccounts();

    void deleteById(Long id);

    void transferFunds(TransferFundsDTO transferFundsDTO);

    List<TransactionDTO> getAccTransactions(Long accId);

}
