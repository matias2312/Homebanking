package com.minhub.homebanking.Services.Implements;


import com.minhub.homebanking.DTO.TransactionRequestDTO;
import com.minhub.homebanking.Services.TransactionService;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.Transaction;
import com.minhub.homebanking.models.TransactionType;
import com.minhub.homebanking.repository.BankDAO;
import com.minhub.homebanking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImplement implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankDAO bankDAO;

    @Override
    public void saveTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }

    @Override
    public Set<Transaction> filterTransactionsWithDate(LocalDateTime fromDate, LocalDateTime toDate, Account account){
        return transactionRepository.findByDateBetween(fromDate, toDate).stream().filter(transaction -> transaction.getAccount() == account).collect(Collectors.toSet());
    }

    @Override
    public ResponseEntity<Object> newTransaction(TransactionRequestDTO transactionRequestDTO, Authentication authentication) {
        Client client = bankDAO.findByClientEmail(authentication.getName());
        Account newAccountOrigin = bankDAO.getAccountFindByNumber(transactionRequestDTO.getAccountOrigin());
        Account newAccountDestiny =  bankDAO.getAccountFindByNumber(transactionRequestDTO.getAccountDestiny());

        if(client == null){
            return new ResponseEntity<>("Client does not exist", HttpStatus.FORBIDDEN);
        }
        if(transactionRequestDTO.getAmount() <= 0 ||  transactionRequestDTO.getAmount() == null){
            return new ResponseEntity<>("Amount not available", HttpStatus.FORBIDDEN);
        }
        if(newAccountOrigin.getActive() == false){
            return new ResponseEntity<>("account not available", HttpStatus.FORBIDDEN);
        }
        if(newAccountDestiny.getActive() == false){
            return new ResponseEntity<>("account not available", HttpStatus.FORBIDDEN);
        }
        if (transactionRequestDTO.getDescription().isEmpty() || transactionRequestDTO.getAccountOrigin().isEmpty() || transactionRequestDTO.getAccountOrigin().isEmpty()) {
            return new ResponseEntity<>("Missing data to complete", HttpStatus.FORBIDDEN);
        }
        if(transactionRequestDTO.getAccountOrigin().equals(transactionRequestDTO.getAccountDestiny())){
            return new ResponseEntity<>("Accounts cannot be the same", HttpStatus.FORBIDDEN);
        }
        if(newAccountOrigin == null){
            return new ResponseEntity<>("Origin account doesn´t exist", HttpStatus.FORBIDDEN);
        }
        if(newAccountDestiny == null) {
            return new ResponseEntity<>("Destiny account doesn´t exist", HttpStatus.FORBIDDEN);
        }
        if(bankDAO.getAccountFindByNumber(transactionRequestDTO.getAccountOrigin()).getBalance() < transactionRequestDTO.getAmount()){
            return new ResponseEntity<>("Not enough money", HttpStatus.FORBIDDEN);
        }
        Double balance1 = newAccountOrigin.getBalance() - transactionRequestDTO.getAmount();
        Double balance2 = newAccountDestiny.getBalance() + transactionRequestDTO.getAmount();
        bankDAO.createTransaction(newAccountOrigin, TransactionType.DEBIT,-transactionRequestDTO.getAmount(), transactionRequestDTO.getDescription() + " go to " + transactionRequestDTO.getAccountDestiny(), LocalDateTime.now(), balance1);
        bankDAO.createTransaction(newAccountDestiny, TransactionType.CREDIT, transactionRequestDTO.getAmount(), transactionRequestDTO.getDescription() + " from to " + transactionRequestDTO.getAccountOrigin(),LocalDateTime.now(), balance2);
        newAccountOrigin.setBalance(newAccountOrigin.getBalance() - transactionRequestDTO.getAmount());
        newAccountDestiny.setBalance(newAccountDestiny.getBalance() + transactionRequestDTO.getAmount());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
