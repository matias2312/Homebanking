package com.minhub.homebanking.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.ClientLoan;
import com.minhub.homebanking.models.Loan;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClientLoanDTO {
    private Long id;
    private long loanid;
    private String name;
    private Double amount;
    private Integer payments;

    public ClientLoanDTO(ClientLoan clientLoan) {
       this.id = clientLoan.getId();
       this.amount = clientLoan.getAmount();
       this.payments = clientLoan.getPayments();
       this.name = clientLoan.getLoan().getName();
       this.loanid= clientLoan.getLoan().getId();
    }
}
