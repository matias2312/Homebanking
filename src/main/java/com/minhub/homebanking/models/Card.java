package com.minhub.homebanking.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    private String cardHolder;
    private String number;
    private int cvv;
    private LocalDateTime thruDate;
    private LocalDateTime fromDate;
    private CardColor cardColor;
    private CardType cardType;
    private Boolean active;

    public Card() {
    }

    public Card(Client client, String cardHolder, String number, int cvv, LocalDateTime thruDate, LocalDateTime fromDate,CardColor cardColor,CardType cardType,Boolean active,Account account) {
        this.client = client;
        this.account = account;
        this.cardHolder = cardHolder;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.cardColor = cardColor;
        this.cardType = cardType;
        this.active =  active;
    }

}
