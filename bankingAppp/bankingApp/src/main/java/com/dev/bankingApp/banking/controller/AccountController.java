package com.dev.bankingApp.banking.controller;

import com.dev.bankingApp.banking.dto.AccountDTO;
import com.dev.bankingApp.banking.dto.TransactionDTO;
import com.dev.bankingApp.banking.dto.TransferFundsDTO;
import com.dev.bankingApp.banking.entity.Account;
import com.dev.bankingApp.banking.repository.AccountRepository;
import com.dev.bankingApp.banking.repository.TransactionRepository;
import com.dev.bankingApp.banking.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/banking")
public class AccountController {


    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO){
        return new ResponseEntity<>(accountService.createAccount(accountDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id){
        AccountDTO accountDTO = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDTO);
    }

    @PutMapping("/{id}/deposite")
    public ResponseEntity<AccountDTO> deposite(@PathVariable Long id, @RequestBody Map<String, Double> request){
        double amount = request.get("amount");
        AccountDTO accountDTO = accountService.deposite(id, amount);
        return ResponseEntity.ok(accountDTO);
    }

    @PutMapping("/{id}/credit")
    public ResponseEntity<AccountDTO> credit(@PathVariable Long id, @RequestBody Map<String, Double> request){
        double amount = request.get("amount");
        AccountDTO accountDTO = accountService.credit(id, amount);
        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("/allAccounts")
    public ResponseEntity<List<AccountDTO>> getAllAccounts(){
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{id}/deleteAccount")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        accountService.deleteById(id);
        return ResponseEntity.ok("account deleted successfully...");
    }

    @PostMapping("/transferingFunds")
    public ResponseEntity<String> transferFunds(@RequestBody TransferFundsDTO transferFundsDTO){
        accountService.transferFunds(transferFundsDTO);
        return ResponseEntity.ok("Transfer Successful...");
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDTO>> getAccTransactions(@PathVariable("id") Long accId){
        List<TransactionDTO> transactions = accountService.getAccTransactions(accId);
        return ResponseEntity.ok(transactions);
    }



}
