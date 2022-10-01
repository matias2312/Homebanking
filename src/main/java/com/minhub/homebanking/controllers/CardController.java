package com.minhub.homebanking.controllers;


import com.minhub.homebanking.Services.AccountService;
import com.minhub.homebanking.Services.CardService;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.models.*;
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
    private final ClientService clientService;
    private final AccountService accountService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> newCard(Authentication authentication, @RequestParam CardType cardType, @RequestParam CardColor cardColor,@RequestParam String accountOrigin) {
        String cardNumber = CardUtils.getCardNumber();
        int cvv = CardUtils.getCardCvv();
        Client client = clientService.findByClientEmail(authentication.getName());
        Account account = accountService.getFindByNumber(accountOrigin);

        if(cardType == null || cardColor == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (account == null){
            return new ResponseEntity<>("Missing data account", HttpStatus.FORBIDDEN);
        }
        if(!account.getNumber().contains(accountOrigin)){
            return new ResponseEntity<>("Account origin does not exist", HttpStatus.FORBIDDEN);
        }
        if(client.getCards().stream().anyMatch(card -> card.getCardColor().equals(cardColor) && card.getCardType().equals(cardType) && card.getActive().equals(true))){
            return new ResponseEntity<>("for each card you can only select one color", HttpStatus.FORBIDDEN);
        }

        cardService.saveCard(new Card(client, client.toString(), cardNumber, cvv, LocalDateTime.now().plusYears(5), LocalDateTime.now(), cardColor, cardType,true,account));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PatchMapping("/clients/current/cards/delete")
    public ResponseEntity<Object> deleteCard(Authentication authentication,@RequestParam Long cardId ){
        Client client = clientService.findByClientEmail(authentication.getName());
        Card card = cardService.getCardById(cardId);
        if(card == null){
            return new ResponseEntity<>("the card does not exist", HttpStatus.FORBIDDEN);
        }
        if(client == null){
            return new ResponseEntity<>("client does not exist", HttpStatus.FORBIDDEN);
        }
        if(!client.getCards().contains(card)){
            return new ResponseEntity<>("card does not exist", HttpStatus.FORBIDDEN);
        }
        cardService.deleteCard(card);
        return new ResponseEntity<>("card deleted successfully", HttpStatus.ACCEPTED);

    }

}