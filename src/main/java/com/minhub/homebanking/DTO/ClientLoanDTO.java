package com.minhub.homebanking.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.ClientLoan;
import com.minhub.homebanking.models.Loan;

public class ClientLoanDTO {

    private Long id;
    private long loanid;
    private String name;
    private Double amount;
    private Integer payments;

    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientLoan) {
       this.id = clientLoan.getId();
       this.amount = clientLoan.getAmount();
       this.payments = clientLoan.getPayments();
       this.name = clientLoan.getLoan().getName();
       this.loanid= clientLoan.getLoan().getId();
       
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }
    
    public String getName() {
        return name;
    }

    public long getLoanid() {
        return loanid;
    }
}
