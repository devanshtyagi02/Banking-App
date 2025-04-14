package com.dev.bankingApp.banking.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record TransactionDTO(Long id,
                             Long accId,
                             double amount,
                             String transactionType,
                             LocalDateTime timeStamp) {
}
