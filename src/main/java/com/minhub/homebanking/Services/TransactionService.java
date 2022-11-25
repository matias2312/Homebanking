package com.minhub.homebanking.Services;

import com.minhub.homebanking.DTO.TransactionRequestDTO;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Set;

public interface TransactionService {
     void saveTransaction(Transaction transaction);
     Set<Transaction> filterTransactionsWithDate(LocalDateTime fromDate, LocalDateTime toDate, Account account);
     ResponseEntity<Object> newTransaction(TransactionRequestDTO transactionRequestDTO, Authentication authentication);
}
