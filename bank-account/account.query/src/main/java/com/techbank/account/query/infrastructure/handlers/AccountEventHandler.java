package com.techbank.account.query.infrastructure.handlers;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositedEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;
import com.techbank.account.query.domain.AccountRepository;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.account.query.domain.BankTransaction;
import com.techbank.account.query.domain.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountEventHandler implements EventHandler {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreatedDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();
        accountRepository.save(bankAccount);
        var transaction = BankTransaction.builder()
                .id(UUID.randomUUID().toString())
                .bankAccountId(event.getId())
                .amount(event.getOpeningBalance())
                .transactionDate(event.getCreatedDate())
                .transactionType(event.getTransactionType())
                .build();
        transactionRepository.save(transaction);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty()) {
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance + event.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
        var transaction = BankTransaction.builder()
                .id(UUID.randomUUID().toString())
                .bankAccountId(event.getId())
                .amount(event.getAmount())
                .transactionDate(event.getTransactionDate())
                .transactionType(event.getTransactionType())
                .build();
        transactionRepository.save(transaction);
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty()) {
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance - event.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());
        var transaction = BankTransaction.builder()
                .id(UUID.randomUUID().toString())
                .bankAccountId(event.getId())
                .amount(event.getAmount())
                .transactionDate(event.getTransactionDate())
                .transactionType(event.getTransactionType())
                .build();
        transactionRepository.save(transaction);
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
        var transaction = BankTransaction.builder()
                .id(UUID.randomUUID().toString())
                .bankAccountId(event.getId())
                .transactionDate(event.getTransactionDate())
                .transactionType(event.getTransactionType())
                .build();
        transactionRepository.save(transaction);
    }
}