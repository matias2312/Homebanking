package com.minhub.homebanking.Services.Implements;

import com.minhub.homebanking.DTO.AccountDTO;
import com.minhub.homebanking.Services.AccountService;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.exception.BankException;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repository.AccountRepository;
import com.minhub.homebanking.repository.BankDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImplement implements AccountService {
    private final AccountRepository accountRepository;
    private final BankDAO bankDAO;
    @Override
    public List<Account> getAccounts(){
        return accountRepository.findAll();
    }
    @Override
    public AccountDTO getAccountById(Long Id){
        Account accountExist = bankDAO.getAccountById(Id);
        if(accountExist == null) {
            throw new BankException("The requested account ID does not exist");
        }
        AccountDTO accountDTO = new AccountDTO(accountExist);
        return accountDTO ;
    }
    @Override
    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    @Override
    public ResponseEntity<Object> newAccount(AccountDTO accountDTO, Authentication authentication) {
        Client client = bankDAO.findByClientEmail(authentication.getName());
        if(client == null){
            return new ResponseEntity<>("client does not exist", HttpStatus.FORBIDDEN);
        }
        if(accountDTO.getType() == null){
            return new ResponseEntity<>("complete account type", HttpStatus.FORBIDDEN);
        }
        if (client.getAccounts().stream().filter(account -> account.getActive() == true).count() >= 3) {
            return new ResponseEntity<>("you can not create more accounts", HttpStatus.FORBIDDEN);
        }
        Account account = bankDAO.createAccount(accountDTO, client);
        return new ResponseEntity<>("successfully created", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> deleteAccount(String number, Authentication authentication) {
        Client client = bankDAO.findByClientEmail(authentication.getName());
        if(client == null){
            return new ResponseEntity<>("client does not exist", HttpStatus.FORBIDDEN);
        }
        if(number.isEmpty()){
            return new ResponseEntity<>("number is empty", HttpStatus.FORBIDDEN);
        }
        if(number == null){
            return new ResponseEntity<>("number does not exist", HttpStatus.FORBIDDEN);
        }
        Account account = bankDAO.getAccountFindByNumber(number);
        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("the account you want to delete does not exist", HttpStatus.FORBIDDEN);
        }
        if(account.getBalance() > 0){
            return new ResponseEntity<>("You cannot delete an account with a balance greater than 0", HttpStatus.FORBIDDEN);
        }
        bankDAO.deleteAccount(number);
        return new ResponseEntity<>("account deleted successfully", HttpStatus.OK);
    }
}
