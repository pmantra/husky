package com.techbank.account.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditCardTransaction {
    private String transactionDate;
    private String description;
    private String category;
    private CreditCardTransactionType transactionType;
    private BigDecimal amount;
}
