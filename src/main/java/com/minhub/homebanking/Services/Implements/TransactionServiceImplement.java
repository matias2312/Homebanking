package com.minhub.homebanking.Services.Implements;


import com.minhub.homebanking.Services.TransactionService;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.Transaction;
import com.minhub.homebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }



    @Override
    public Set<Transaction> filterTransactionsWithDate(LocalDateTime fromDate, LocalDateTime toDate, Account account){
        return transactionRepository.findByDateBetween(fromDate, toDate).stream().filter(transaction -> transaction.getAccount() == account).collect(Collectors.toSet());
    }
}
