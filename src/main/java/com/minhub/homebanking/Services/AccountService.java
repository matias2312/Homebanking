package com.minhub.homebanking.Services;

import com.minhub.homebanking.DTO.AccountDTO;
import com.minhub.homebanking.models.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {
    List<Account> getAccounts();
    AccountDTO getAccountById(Long Id);
    void saveAccount(Account account);
    ResponseEntity<Object> newAccount(AccountDTO accountDTO, Authentication authentication);
    ResponseEntity<Object> deleteAccount(String number, Authentication authentication);
}
