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
    private final BankDAO bankDAO;
    @Transactional
    @PostMapping("/clients/current/loan")
    public ResponseEntity<Object> newLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        return loanService.newLoan(loanApplicationDTO, authentication);
    }
    @PostMapping("/clients/current/loan/create")
    public ResponseEntity<Object> createLoan(@RequestBody LoanDTO loanDTO, Authentication authentication){
        return loanService.createLoan(loanDTO, authentication);
    }
    @GetMapping("/loans")
    public List<LoanDTO> getLoan() {
        return bankDAO.getLoan().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }
}