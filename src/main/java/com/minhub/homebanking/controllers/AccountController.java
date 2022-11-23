package com.minhub.homebanking.controllers;

import com.minhub.homebanking.DTO.AccountDTO;
import com.minhub.homebanking.DTO.ClientDTO;
import com.minhub.homebanking.Services.AccountService;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.configurations.WebAuthentication;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.AccountType;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repository.AccountRepository;
import com.minhub.homebanking.repository.BankDAO;
import com.minhub.homebanking.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }
    @GetMapping("/account/{id}")
    public AccountDTO getAccountId(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> newAccount(@RequestBody AccountDTO accountDTO, Authentication authentication){
        return accountService.newAccount(accountDTO, authentication);
    }
    @PatchMapping("/clients/current/accounts/delete")
    public ResponseEntity<Object> deleteAccount(@RequestParam String number,Authentication authentication){
        return accountService.deleteAccount(number, authentication);
    }

}
