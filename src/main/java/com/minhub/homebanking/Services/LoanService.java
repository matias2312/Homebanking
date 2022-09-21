package com.minhub.homebanking.Services;
import com.minhub.homebanking.models.Loan;
import java.util.List;

public interface LoanService {
    public List<Loan> getLoan();
    public Loan getLoanById(Long Id);
    public void saveLoan(Loan loan);
    public Loan findLoanByName(String name);
}
