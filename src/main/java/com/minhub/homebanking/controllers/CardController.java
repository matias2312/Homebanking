package com.minhub.homebanking.controllers;


import com.minhub.homebanking.DTO.CardDTO;
import com.minhub.homebanking.Services.AccountService;
import com.minhub.homebanking.Services.CardService;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.models.*;
import com.minhub.homebanking.repository.BankDAO;
import com.minhub.homebanking.utils.CardUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> newCard(@RequestBody CardDTO cardDTO, Authentication authentication) {
        return cardService.newCard(cardDTO,authentication);
    }
    @PatchMapping("/clients/current/cards/delete")
    public ResponseEntity<Object> deleteCard(@RequestParam Long cardId, Authentication authentication){
        return cardService.deleteCard(cardId, authentication);
    }
}