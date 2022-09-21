package com.minhub.homebanking.Services.Implements;

import com.minhub.homebanking.DTO.LoanDTO;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.Services.LoanService;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.Loan;
import com.minhub.homebanking.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImplement implements LoanService{

    @Autowired
    private LoanRepository loanRepository;

    @Override
   public List<Loan> getLoan(){
        return loanRepository.findAll();
    }
    @Override
    public Loan getLoanById(Long Id){
        return loanRepository.findById(Id).get();
    }
    @Override
    public void saveLoan(Loan loan){
        loanRepository.save(loan);
    }
    @Override
    public Loan findLoanByName(String name){
        return loanRepository.findByName(name);
    }
}
