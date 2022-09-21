package com.minhub.homebanking.DTO;

import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.ClientLoan;
import com.minhub.homebanking.models.Loan;
import com.minhub.homebanking.models.Transaction;

import java.util.List;

public class LoanApplicationDTO {

    private long id;

    private Double amount;

    private Integer payments;

    private String accountDestiny;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(Loan loan, Double amount, Integer payments, String accountDestiny) {
        this.id = loan.getId();
        this.amount = amount;
        this.payments = payments;
        this.accountDestiny = accountDestiny;
    }

    public long getId() {
        return id;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public String getAccountDestiny() {
        return accountDestiny;
    }

    public void setAccountDestiny(String accountDestiny) {
        this.accountDestiny = accountDestiny;
    }
}
