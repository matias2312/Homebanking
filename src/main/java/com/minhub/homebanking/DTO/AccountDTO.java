package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.AccountType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
@Getter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private Double balance;
    private Boolean active;
    private Set<TransactionDTO> transaction;
    private AccountType type;
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transaction = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.active = account.getActive();
        this.type = account.getType();

    }


}
