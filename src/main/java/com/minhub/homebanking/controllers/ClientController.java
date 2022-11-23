package com.minhub.homebanking.controllers;

import com.minhub.homebanking.DTO.ClientDTO;
import com.minhub.homebanking.DTO.UserRequestDTO;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.repository.BankDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final BankDAO bankDAO;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClients().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClientId(id);
    }
    @PostMapping("/clients")
    public ResponseEntity<Object> register(@RequestBody UserRequestDTO user) {
        return clientService.register(user);
    }
    @GetMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication) {
        return new ClientDTO(bankDAO.findByClientEmail(authentication.getName()));
    }

}






