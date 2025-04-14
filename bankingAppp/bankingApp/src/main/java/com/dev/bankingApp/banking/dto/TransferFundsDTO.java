package com.dev.bankingApp.banking.dto;

public record TransferFundsDTO(Long fromAcc,
                               Long toAcc,
                               double amount) {  }
