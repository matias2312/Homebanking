package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private Double balance;
    private Boolean active;

    private Set<TransactionDTO> transaction;
    private AccountType type;



    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transaction = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.active = account.getActive();
        this.type = account.getType();

    }


    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransaction() {
        return transaction;
    }

    public Boolean getActive() {
        return active;
    }

    public AccountType getType() {
        return type;
    }


}
