package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
@Getter
@NoArgsConstructor
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
    private String accountOrigin;

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
        this.accountOrigin = card.getAccount().getNumber();
    }


}
