package com.minhub.homebanking.Services;

import com.minhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {
    public List<Account> getAccounts();
    public Account getAccountById(Long Id);
    public Account  getFindByNumber(String number);
    public void saveAccount(Account account);

}
