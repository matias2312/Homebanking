package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Transaction;
import com.minhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionDTO {

    private long id;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime date;

    private Double balance;

    public TransactionDTO() {
    }

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.balance = transaction.getBalance();

    }

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Double getBalance() {
        return balance;
    }
}
