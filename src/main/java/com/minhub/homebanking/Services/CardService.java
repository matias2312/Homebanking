package com.minhub.homebanking.Services;

import com.minhub.homebanking.models.Card;

public interface CardService {
    public void saveCard(Card card);
    public Boolean deleteCard(Card card);
    public Card getCardById(Long Id);
    public Card getCardByNumber(String number);
}
