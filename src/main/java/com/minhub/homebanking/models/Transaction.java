package com.minhub.homebanking.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Setter(AccessLevel.NONE)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="transaction_id")
    private Account account;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime date;
    private Double balance;

    public Transaction(Account account, TransactionType type, Double amount, String description, LocalDateTime date,Double balance) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.balance = balance;
    }

}
