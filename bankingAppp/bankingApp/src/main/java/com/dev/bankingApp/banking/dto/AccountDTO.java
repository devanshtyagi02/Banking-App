package com.dev.bankingApp.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


//public class AccountDTO {
//
//    private long id;
//    private String accHolderName;
//    private double accBalance;
//
//}

//@Data
//@AllArgsConstructor
public record AccountDTO( Long id,
                          String accHolderName,
                          double accBalance
                          ){  }