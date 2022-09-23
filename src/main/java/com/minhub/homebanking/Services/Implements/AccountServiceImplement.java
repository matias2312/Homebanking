package com.minhub.homebanking.Services.Implements;

import com.minhub.homebanking.DTO.AccountDTO;
import com.minhub.homebanking.Services.AccountService;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> getAccounts(){
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long Id){
        return  accountRepository.findById(Id).get();
    }

    @Override
    public Account getFindByNumber(String number){
        return  accountRepository.findByNumber(number);
    }

    @Override
    public void saveAccount(Account account){
        accountRepository.save(account);
    }
}
