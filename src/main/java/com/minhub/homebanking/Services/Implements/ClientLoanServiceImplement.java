package com.minhub.homebanking.Services.Implements;

import com.minhub.homebanking.Services.ClientLoanService;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.models.ClientLoan;
import com.minhub.homebanking.models.Loan;
import com.minhub.homebanking.repository.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImplement implements ClientLoanService {
    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Override
    public void saveClientLoan(ClientLoan clientLoan){
        clientLoanRepository.save(clientLoan);
    }


}
