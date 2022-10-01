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
import com.minhub.homebanking.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
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
    private final ClientService clientService;
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min); }
    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }
    @GetMapping("/account/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return new AccountDTO(accountService.getAccountById(id));
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> newAccount(Authentication authentication, @RequestParam AccountType accountType){
        String accountNumber = "VIN-" + getRandomNumber(1000000,99999999);
        Client client = clientService.findByClientEmail(authentication.getName());

        if(client == null){
            return new ResponseEntity<>("client does not exist", HttpStatus.FORBIDDEN);
        }
        if(accountType == null){
            return new ResponseEntity<>("complete account type", HttpStatus.FORBIDDEN);
        }
        if (client.getAccounts().stream().filter(account -> account.getActive() == true).count() >= 3) {
            return new ResponseEntity<>("you can not create more accounts", HttpStatus.FORBIDDEN);
        }
        accountService.saveAccount(new Account(accountNumber, LocalDateTime.now(),0.0,client,true, accountType));
        return new ResponseEntity<>(HttpStatus.CREATED);
        }
    @PatchMapping("/clients/current/accounts/delete")
    public ResponseEntity<Object> deleteAccount(Authentication authentication,@RequestParam String number){
        Client client = clientService.findByClientEmail(authentication.getName());
        Account account = accountService.getFindByNumber(number);

        if(client == null){
            return new ResponseEntity<>("client does not exist", HttpStatus.FORBIDDEN);
        }
        if(number.isEmpty()){
            return new ResponseEntity<>("number is empty", HttpStatus.FORBIDDEN);
        }
        if(number == null){
            return new ResponseEntity<>("number does not exist", HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("the account you want to delete does not exist", HttpStatus.FORBIDDEN);
        }
        if(account.getBalance() > 0){
            return new ResponseEntity<>("You cannot delete an account with a balance greater than 0", HttpStatus.FORBIDDEN);
        }
        account.setActive(false);
        accountService.saveAccount(account);
        return new ResponseEntity<>("account deleted successfully", HttpStatus.OK);
    }


}
