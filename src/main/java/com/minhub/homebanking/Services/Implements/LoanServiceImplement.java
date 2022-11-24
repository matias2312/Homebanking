package com.minhub.homebanking.Services.Implements;

import com.minhub.homebanking.DTO.LoanApplicationDTO;
import com.minhub.homebanking.DTO.LoanDTO;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.Services.LoanService;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.Loan;
import com.minhub.homebanking.models.TransactionType;
import com.minhub.homebanking.repository.BankDAO;
import com.minhub.homebanking.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImplement implements LoanService{
    private final BankDAO bankDAO;
    private final LoanRepository loanRepository;

    @Override
    public void saveLoan(Loan loan) {
       loanRepository.save(loan);
    }

    @Override
    public ResponseEntity<Object> newLoan(LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        Client client = bankDAO.findByClientEmail(authentication.getName());
        Loan loan = bankDAO.getLoanById(loanApplicationDTO.getId());
        Account account = bankDAO.getAccountFindByNumber(loanApplicationDTO.getAccountDestiny());
        if (client == null) {
            return new ResponseEntity<>("Client does not exist", HttpStatus.FORBIDDEN);
        }
        if (client.getClientLoans().stream().filter(loan1 -> loan1.getLoan().getName().equals(loan.getName())).count() >= 1) {
            return new ResponseEntity<>("the loan is already taken", HttpStatus.FORBIDDEN);
        }
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
        bankDAO.createClientLoan(loanint, loanApplicationDTO.getPayments(), client, loan);
        bankDAO.createTransaction(account, TransactionType.CREDIT, loanApplicationDTO.getAmount(), " loan approved " + loan.getName(), LocalDateTime.now(), balance1);
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> createLoan(LoanDTO loanDTO, Authentication authentication) {
        Client admin = bankDAO.findByClientEmail(authentication.getName());
        if (admin == null) {
            return new ResponseEntity<>("admin does not exist", HttpStatus.FORBIDDEN);
        }
        if(loanDTO.getMaxAmount() <= 0){
            return new ResponseEntity<>("amount invalid", HttpStatus.FORBIDDEN);
        }
        if(loanDTO.getName().isEmpty()){
            return new ResponseEntity<>("invalid name", HttpStatus.FORBIDDEN);
        }
        if(bankDAO.getLoan().stream().map(loan -> loan.getName()).toString().contains(loanDTO.getName())){
            return new ResponseEntity<>("the loan has already been taken", HttpStatus.FORBIDDEN);
        }
        bankDAO.createLoan(loanDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
