package com.minhub.homebanking.repository;

import com.minhub.homebanking.DTO.*;
import com.minhub.homebanking.models.*;
import com.minhub.homebanking.utils.CardUtils;
import lombok.RequiredArgsConstructor;;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
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

    //LOAN
    public Loan findLoanByName(String name){
        return loanRepository.findByName(name);
    }
    public void saveLoan(Loan loan){
        loanRepository.save(loan);
    }
    public Loan getLoanById(Long Id){
        return loanRepository.findById(Id).get();
    }
    public List<Loan> getLoan(){
        return loanRepository.findAll();
    }
    public Loan createLoan(LoanDTO loanDTO){
        Loan loan = Loan.builder()
                .name(loanDTO.getName())
                .maxAmount(loanDTO.getMaxAmount())
                .interest(loanDTO.getInterest())
                .payments(loanDTO.getPayments())
                .build();
        return loanRepository.saveAndFlush(loan);
    }

    //CLIENTLOAN
    public ClientLoan createClientLoan(double amount, Integer payments, Client client, Loan loan){
        ClientLoan clientLoan = ClientLoan.builder()
                .amount(amount)
                .payments(payments)
                .client(client)
                .loan(loan)
                .build();
        return clientLoanRepository.saveAndFlush(clientLoan);
    }
 //TRANSACTION
    public Transaction createTransaction(Account account, TransactionType type, Double amount, String description, LocalDateTime date,Double balance){
        Transaction transaction = Transaction.builder()
                .account(account)
                .amount(amount)
                .type(type)
                .description(description)
                .date(date)
                .balance(balance)
                .build();
        return transactionRepository.saveAndFlush(transaction);
    }
    //CARD
    public Card createCard(CardDTO cardDTO, Client client, Account account){
        String cardNumber = CardUtils.getCardNumber();
        int cvv = CardUtils.getCardCvv();
        Card card = Card.builder()
                .client(client)
                .account(account)
                .cardHolder(client.toString())
                .number(cardNumber)
                .cvv(cvv)
                .thruDate(LocalDateTime.now().plusYears(5))
                .fromDate(LocalDateTime.now())
                .cardColor(cardDTO.getCardColor())
                .cardType(cardDTO.getCardType())
                .active(true)
                .build();
        return cardRepository.saveAndFlush(card);
    }
    public Boolean deleteCard(Card card){
        card.setActive(false);
        cardRepository.save(card);
        return true;
    }
    public Card getCardById(Long Id){
        return  cardRepository.findById(Id).get();
    }
}
