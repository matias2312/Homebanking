package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.ClientLoan;
import com.minhub.homebanking.models.Loan;
import com.minhub.homebanking.models.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class LoanApplicationDTO {
    private long id;
    private Double amount;
    private Integer payments;
    private String accountDestiny;
    public LoanApplicationDTO(Loan loan, Double amount, Integer payments, String accountDestiny) {
        this.id = loan.getId();
        this.amount = amount;
        this.payments = payments;
        this.accountDestiny = accountDestiny;
    }
}
