package com.minhub.homebanking.Services;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Transaction;

import java.time.LocalDateTime;
import java.util.Set;

public interface TransactionService {
    public void saveTransaction(Transaction transaction);
    public Set<Transaction> filterTransactionsWithDate(LocalDateTime fromDate, LocalDateTime toDate, Account account);
}
