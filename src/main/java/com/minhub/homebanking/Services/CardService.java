package com.minhub.homebanking.Services;

import com.minhub.homebanking.DTO.CardDTO;
import com.minhub.homebanking.models.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface CardService {
     void saveCard(Card card);
     Card getCardByNumber(String number);
     ResponseEntity<Object> newCard(CardDTO cardDTO, Authentication authentication);
     ResponseEntity<Object> deleteCard(Long cardId, Authentication authentication);

}
