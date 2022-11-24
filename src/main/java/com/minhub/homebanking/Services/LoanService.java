package com.minhub.homebanking.Services;
import com.minhub.homebanking.DTO.LoanApplicationDTO;
import com.minhub.homebanking.DTO.LoanDTO;
import com.minhub.homebanking.models.Loan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LoanService {
     //List<Loan> getLoan();
    // Loan getLoanById(Long Id);
     void saveLoan(Loan loan);
     //Loan findLoanByName(String name);
     ResponseEntity<Object> newLoan(LoanApplicationDTO loanApplicationDTO, Authentication authentication);
     ResponseEntity<Object> createLoan(LoanDTO loanDTO,Authentication authentication);

}
