package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Card;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentsDTO {



    private String cardHolder;

    private String number;

    private Double amount;

    private Integer cvv;

    private LocalDate thruDate;

    private String description;

    public PaymentsDTO( String cardHolder, String number, Double amount, Integer cvv, LocalDate thruDate, String description) {

        this.cardHolder = cardHolder;
        this.number = number;
        this.amount = amount;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.description = description;
    }



    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public String getDescription() {
        return description;
    }
}
