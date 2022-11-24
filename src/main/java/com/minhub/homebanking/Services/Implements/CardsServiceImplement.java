package com.minhub.homebanking.Services.Implements;

import com.minhub.homebanking.DTO.CardDTO;
import com.minhub.homebanking.Services.CardService;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Card;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repository.BankDAO;
import com.minhub.homebanking.repository.CardRepository;
import com.minhub.homebanking.utils.CardUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardsServiceImplement implements CardService {
    @Autowired
    CardRepository cardRepository;
    private final BankDAO bankDAO;
    @Override
    public void saveCard(Card card){
        cardRepository.save(card);
    }


/*    @Override
    public Card getCardById(Long Id){
        return  cardRepository.findById(Id).get();
    }*/

    @Override
    public Card getCardByNumber(String number){
        return cardRepository.findByNumber(number);
    }

    @Override
    public ResponseEntity<Object> newCard(CardDTO cardDTO, Authentication authentication) {
        Client client = bankDAO.findByClientEmail(authentication.getName());
        Account account = bankDAO.getAccountFindByNumber(cardDTO.getAccountOrigin());

        if(cardDTO.getCardType() == null || cardDTO.getCardColor() == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (account == null){
            return new ResponseEntity<>("Missing data account", HttpStatus.FORBIDDEN);
        }
        if(!account.getNumber().contains(cardDTO.getAccountOrigin())){
            return new ResponseEntity<>("Account origin does not exist", HttpStatus.FORBIDDEN);
        }
        if(client.getCards().stream().anyMatch(card -> card.getCardColor().equals(cardDTO.getCardColor()) && card.getCardType().equals(cardDTO.getCardType()) && card.getActive().equals(true))){
            return new ResponseEntity<>("for each card you can only select one color", HttpStatus.FORBIDDEN);
        }
        bankDAO.createCard(cardDTO, client, account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> deleteCard(Long cardId, Authentication authentication) {
        Client client = bankDAO.findByClientEmail(authentication.getName());
        Card card = bankDAO.getCardById(cardId);
        if(card == null){
            return new ResponseEntity<>("the card does not exist", HttpStatus.FORBIDDEN);
        }
        if(client == null){
            return new ResponseEntity<>("client does not exist", HttpStatus.FORBIDDEN);
        }
        if(!client.getCards().contains(card)){
            return new ResponseEntity<>("card does not exist", HttpStatus.FORBIDDEN);
        }
        bankDAO.deleteCard(card);
        return new ResponseEntity<>("card deleted successfully", HttpStatus.ACCEPTED);
    }
}
