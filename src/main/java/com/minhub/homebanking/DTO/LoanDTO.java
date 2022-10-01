package com.minhub.homebanking.DTO;


import com.minhub.homebanking.models.Loan;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Getter
@NoArgsConstructor
public class LoanDTO {
    private Long id;
    private String name;
    private Double maxAmount;
    private List<Integer> payments;
    private Double interest;

    public LoanDTO(Loan loan) {
        this.id = loan.getId() ;
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.interest = loan.getInterest();
    }
}
