package com.minhub.homebanking.repository;

import com.minhub.homebanking.DTO.AccountDTO;
import com.minhub.homebanking.DTO.UserRequestDTO;
import com.minhub.homebanking.Services.AccountService;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.email.ValidatorEmail;
import com.minhub.homebanking.email.VerificationEmail;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


@RequiredArgsConstructor
@Component
public class BankDAO {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ClientLoanRepository clientLoanRepository;
    private final LoanRepository loanRepository;
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final PasswordEncoder passwordEncoder;

    //CLIENT
    public Client createUser(UserRequestDTO user){
        Client clientUser = Client.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        return  clientRepository.saveAndFlush(clientUser);
    }

    public Optional<Client> getClientById(Long Id){
        return clientRepository.findById(Id);
    }

    public Client findByClientEmail(String email){
        return clientRepository.findByEmail(email);
    }

    //ACCOUNT
    public Account createAccount(AccountDTO accountDTO, Client client){
        String accountNumber = "VIN-" + getRandomNumber(1,99999999);
        Account account = Account.builder()
                .number(accountNumber)
                .balance(0.0)
                .creationDate(LocalDateTime.now())
                .client(client)
                .active(Boolean.TRUE)
                .type(accountDTO.getType())
                .build();
        return  accountRepository.saveAndFlush(account);
    }
    public Account deleteAccount(String number){
        Account account = getAccountFindByNumber(number);
        account.setActive(false);
        return accountRepository.saveAndFlush(account);
    }
    public Account getAccountById(Long Id){
        return accountRepository.findById(Id).get();
    }
    public Account getAccountFindByNumber(String number){
        return  accountRepository.findByNumber(number);
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
