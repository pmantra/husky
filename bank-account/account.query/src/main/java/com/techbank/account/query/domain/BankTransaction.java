package com.techbank.account.query.domain;

import com.techbank.account.common.dto.TransactionType;
import com.techbank.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BankTransaction extends BaseEntity {
    @Id
    private String id;
    private String bankAccountId;
    private Date transactionDate;
    private TransactionType transactionType;
    private double amount;
}
