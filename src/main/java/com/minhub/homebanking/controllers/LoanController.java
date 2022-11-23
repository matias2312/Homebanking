package com.minhub.homebanking.controllers;

import com.minhub.homebanking.DTO.LoanApplicationDTO;
import com.minhub.homebanking.DTO.LoanDTO;
import com.minhub.homebanking.Services.*;
import com.minhub.homebanking.models.*;
import com.minhub.homebanking.repository.BankDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;
    private final ClientService clientService;
    private final AccountService accountService;
    private final ClientLoanService clientLoanService;
    private final TransactionService transactionService;
    private final BankDAO bankDAO;

    @Transactional
    @PostMapping("/clients/current/loan")
    public ResponseEntity<String> newLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        //Client client = clientService.findByClientEmail(authentication.getName());
        Client client = bankDAO.findByClientEmail(authentication.getName());
        Loan loan = loanService.getLoanById(loanApplicationDTO.getId());
        Account account = bankDAO.getAccountFindByNumber(loanApplicationDTO.getAccountDestiny());
        //crear una prpopiedad % y le damos un valor cuando creamos un prestamo.

        if (client == null) {
            return new ResponseEntity<>("Client does not exist", HttpStatus.FORBIDDEN);
        }
        if (client.getClientLoans().stream().filter(loan1 -> loan1.getLoan().getName().equals(loan.getName())).count() >= 1) {
            return new ResponseEntity<>("the loan is already taken", HttpStatus.FORBIDDEN);
        }// queda para ver
        if (loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getPayments() <= 0) {
            return new ResponseEntity<>("choose an amount or payment", HttpStatus.FORBIDDEN);
        }
        if (loan == null) {
            return new ResponseEntity<>("Loan not found", HttpStatus.FORBIDDEN);
        }
        if (loan.getMaxAmount() < loanApplicationDTO.getAmount()) {
            return new ResponseEntity<>("You cannot exceed the maximum amount", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("The number of payments chosen is not within those available for this loan", HttpStatus.FORBIDDEN);
        }
        if (account == null) {
            return new ResponseEntity<>("the account does not exist", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("the destination account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }

        double loanint = loanApplicationDTO.getAmount() * (loan.getInterest() / 100) + loanApplicationDTO.getAmount();
        Double balance1 = account.getBalance() + loanApplicationDTO.getAmount();
        ClientLoan clientLoan1 = new ClientLoan(loanint, loanApplicationDTO.getPayments(), client, loan);
        Transaction transaction1 = new Transaction(account, TransactionType.CREDIT, loanApplicationDTO.getAmount(), " loan approved " + clientLoan1.getLoan().getName(), LocalDateTime.now(), balance1);
        clientLoanService.saveClientLoan(clientLoan1);
        transactionService.saveTransaction(transaction1);
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/loans")
    public List<LoanDTO> getLoan() {
        return loanService.getLoan().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }
    @PostMapping("/clients/current/loan/create")
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestParam String name,@RequestParam  List<Integer> payments,@RequestParam  Double maxAmount,@RequestParam Double interest ) {
       // Client admin = clientService.findByClientEmail(authentication.getName());
        Client admin = bankDAO.findByClientEmail(authentication.getName());

        if (admin == null) {
            return new ResponseEntity<>("admin does not exist", HttpStatus.FORBIDDEN);
        }
        if(maxAmount <= 0){
            return new ResponseEntity<>("amount invalid", HttpStatus.FORBIDDEN);
        }
        if(name.isEmpty()){
            return new ResponseEntity<>("invalid name", HttpStatus.FORBIDDEN);
        }
        if(loanService.getLoan().stream().map(loan -> loan.getName()).toString().contains(name)){
            return new ResponseEntity<>("the loan has already been taken", HttpStatus.FORBIDDEN);
        }
        Loan loan = new Loan(name,maxAmount,interest,payments);
        loanService.saveLoan(loan);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}