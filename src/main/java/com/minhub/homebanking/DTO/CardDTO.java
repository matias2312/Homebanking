package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Card;
import com.minhub.homebanking.models.CardColor;
import com.minhub.homebanking.models.CardType;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class CardDTO {

    private Long id;
    private String cardHolder;
    private String number;
    private int cvv;
    private LocalDateTime thruDate;
    private LocalDateTime fromDate;
    private CardColor cardColor;
    private CardType cardType;

    private Boolean active;



    public CardDTO() {
    }

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate();
        this.cardColor = card.getCardColor();
        this.cardType = card.getCardType();
        this.active = card.getActive();

    }

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Boolean getActive() {
        return active;
    }

}
