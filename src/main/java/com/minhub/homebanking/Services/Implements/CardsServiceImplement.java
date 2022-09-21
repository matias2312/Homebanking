package com.minhub.homebanking.Services.Implements;

import com.minhub.homebanking.Services.CardService;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Card;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardsServiceImplement implements CardService {
    @Autowired
    CardRepository cardRepository;
    @Override
    public void saveCard(Card card){
        cardRepository.save(card);
    }

    @Override
    public Boolean deleteCard(Card card){
        card.setActive(false);
        cardRepository.save(card);
        return true;
    }
    @Override
    public Card getCardById(Long Id){
        return  cardRepository.findById(Id).get();
    }

    @Override
    public Card getCardByNumber(String number){
        return cardRepository.findByNumber(number);
    }
}
