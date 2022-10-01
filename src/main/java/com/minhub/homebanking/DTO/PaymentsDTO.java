package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Card;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
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

}
