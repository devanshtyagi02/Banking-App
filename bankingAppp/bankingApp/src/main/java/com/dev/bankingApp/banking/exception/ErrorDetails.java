package com.dev.bankingApp.banking.exception;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime time,
                           String msg,
                           String detail,
                           String errorCode) {  }
