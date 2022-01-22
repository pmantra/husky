package com.techbank.account.common.events;

import com.techbank.account.common.dto.TransactionType;
import com.techbank.cqrs.core.events.BaseEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
public class AccountClosedEvent extends BaseEvent {
    private TransactionType transactionType;
    private Date transactionDate;
}
