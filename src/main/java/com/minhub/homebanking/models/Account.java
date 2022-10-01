package com.minhub.homebanking.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Setter(AccessLevel.NONE)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)//EAGER nos mantiene la inf actualizada
    @JoinColumn(name="client_id")
    private Client client;
    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();
    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();
    private String number;
    private LocalDateTime creationDate;
    private Double balance;
    private Boolean active;
    private AccountType type;
    public Account(String number, LocalDateTime creationDate, Double balance,Client client, Boolean active, AccountType type) {
        this.client = client;
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.active = active;
        this.type = type;
    }

}
