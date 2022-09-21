package com.minhub.homebanking.controllers;

import com.minhub.homebanking.DTO.ClientDTO;
import com.minhub.homebanking.Services.AccountService;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.configurations.WebAuthentication;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.AccountType;
import com.minhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebAuthentication webAuthentication;

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClients().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return new ClientDTO(clientService.getClientById(id));
    }
    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        String accountNumber = "VIN-" + getRandomNumber(1,99999999);

        if (firstName.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (lastName.isEmpty() ) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (email.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.findByClientEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        if(email.contains("admin@mindhub.com")){
            return new ResponseEntity<>("Invalid user", HttpStatus.FORBIDDEN);
        }

        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(newClient);
        accountService.saveAccount(new Account(accountNumber, LocalDateTime.now(),0.0,newClient,true, AccountType.SAVING));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication) {
        return new ClientDTO(clientService.findByClientEmail(authentication.getName()));
    }

}






