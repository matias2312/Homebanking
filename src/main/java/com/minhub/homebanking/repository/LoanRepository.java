package com.minhub.homebanking.repository;

import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Loan findByMaxAmount(Double MaxAmount);
    Loan findByName(String name);
}
