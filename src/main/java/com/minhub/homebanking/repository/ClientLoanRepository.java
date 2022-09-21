package com.minhub.homebanking.repository;

import com.minhub.homebanking.models.ClientLoan;
import com.minhub.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {

}
