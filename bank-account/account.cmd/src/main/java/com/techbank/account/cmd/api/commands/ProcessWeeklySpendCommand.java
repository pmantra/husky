package com.techbank.account.cmd.api.commands;

import com.techbank.account.common.dto.CreditCardTransaction;
import com.techbank.account.common.dto.CreditCardType;
import com.techbank.cqrs.core.commands.BaseCommand;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProcessWeeklySpendCommand extends BaseCommand {
    private String accountName;
    private String accountHolder;
    private CreditCardType creditCardType;
    private Date reportDate;
    private List<CreditCardTransaction> transactions;
}
