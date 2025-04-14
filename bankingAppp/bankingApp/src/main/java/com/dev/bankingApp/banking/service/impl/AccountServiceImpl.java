package com.dev.bankingApp.banking.service.impl;

import com.dev.bankingApp.banking.dto.AccountDTO;
import com.dev.bankingApp.banking.dto.TransactionDTO;
import com.dev.bankingApp.banking.dto.TransferFundsDTO;
import com.dev.bankingApp.banking.entity.Account;
import com.dev.bankingApp.banking.entity.Transaction;
import com.dev.bankingApp.banking.exception.AccountException;
import com.dev.bankingApp.banking.mapper.AccountMapper;
import com.dev.bankingApp.banking.repository.AccountRepository;
import com.dev.bankingApp.banking.repository.TransactionRepository;
import com.dev.bankingApp.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private static final String TRANSACTION_TYPE_DEPOSITE = "DEPOSIT";
    private static final String TRANSACTION_TYPE_WITHDRAW = "WITHDRAW";


    public AccountServiceImpl(AccountRepository accountRepository,
                              TransactionRepository transactionRepository){
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }



    @Override
    public AccountDTO createAccount(AccountDTO accountDTO){
        Account account = AccountMapper.mapToAccount(accountDTO);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDTO(savedAccount);
    }

    @Override
    public AccountDTO getAccountById(Long id){
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()-> new AccountException("Account doesnt Exists..."));
        return AccountMapper.mapToAccountDTO(account);
    }

    @Override
    public AccountDTO deposite(Long id, double amount){
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()-> new AccountException("Account doesnt Exists..."));

        double total = account.getAccBalance() + amount;
        account.setAccBalance(total);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_DEPOSITE);
        transaction.setTimeStamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDTO(savedAccount);
    }

    @Override
    public AccountDTO credit(Long id, double amount){
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()-> new AccountException("Account doesnt Exists..."));

        if(amount> account.getAccBalance()){
            throw new AccountException("Low Balance...");
        }
        double total = account.getAccBalance() - amount;
        account.setAccBalance(total);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_WITHDRAW);
        transaction.setTimeStamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDTO(savedAccount);
    }

    @Override
    public List<AccountDTO> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account)->AccountMapper.mapToAccountDTO(account))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id){
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()-> new AccountException("Account doesnt Exists..."));
        accountRepository.deleteById(id);
    }

    @Override
    public void transferFunds(TransferFundsDTO transferFundsDTO){

        Account fromAccount = accountRepository
                .findById(transferFundsDTO.fromAcc())
                .orElseThrow(()-> new AccountException("Account doesnt Exists..."));
        Account toAccount = accountRepository
                .findById(transferFundsDTO.toAcc())
                .orElseThrow(()-> new AccountException("Account doesnt Exists..."));

        credit(fromAccount.getId(), transferFundsDTO.amount());
        accountRepository.save(fromAccount);

        deposite(toAccount.getId(), transferFundsDTO.amount());
        accountRepository.save(toAccount);

    }

    @Override
    public List<TransactionDTO> getAccTransactions(Long accId){
        List<Transaction> transactions = transactionRepository
                .findByAccIdOrderByTimeStampDesc(accId);

        return transactions.stream()
                .map((transaction) -> convertEntityToDTO(transaction))
                .collect(Collectors.toList());
    }

    private TransactionDTO convertEntityToDTO(Transaction transaction){
        return new TransactionDTO(
                transaction.getId(),
                transaction.getAccId(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getTimeStamp()
        );
    }

}
