package com.minhub.homebanking;

import com.minhub.homebanking.Services.*;
import com.minhub.homebanking.models.*;
import com.minhub.homebanking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static com.minhub.homebanking.models.CardColor.GOLD;
import static com.minhub.homebanking.models.TransactionType.CREDIT;
import static com.minhub.homebanking.models.TransactionType.DEBIT;
import static java.util.Arrays.asList;


@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);}

		@Bean
		public CommandLineRunner initData (ClientService clientService, AccountService accountService, TransactionService transactionService, LoanService loanService,ClientLoanService clientLoanService ,CardService cardService){
			return (args) -> {
//			en esta intancia el cliente  guardado en la variable vive en la memoia de la aplicacion java
				Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("holamundo"));
				Client client2 = new Client("riki", "ford", "riki@mindhub.com",passwordEncoder.encode("holamundo2"));
				Client client3 = new Client("matias", "segovia", "segovia@mindhub.com",passwordEncoder.encode("holamundo3"));
				Client admin = new Client("admin","admin","admin@mindhub.com",passwordEncoder.encode("holamundo4"));
				Account cuenta1 = new Account("VIN001", LocalDateTime.now(), 5000.0, client1, true,AccountType.SAVING);
				Account cuenta2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500.0,client1,true,AccountType.CURRENT);
				Account cuenta3 = new Account("VIN003", LocalDateTime.now().plusDays(2), 8500.0,client2,true,AccountType.CURRENT);
				Transaction transaction1 = new Transaction(cuenta1, TransactionType.CREDIT, 500.00, "Transaction", LocalDateTime.now(),cuenta1.getBalance());
				Transaction transaction2 = new Transaction(cuenta1, TransactionType.DEBIT, -400.00, "PedidosYa", LocalDateTime.now().plusDays(-1), cuenta1.getBalance());
				Transaction transaction3 = new Transaction(cuenta1, TransactionType.CREDIT, 800.00, "PedidosYa", LocalDateTime.now().plusDays(-2), cuenta1.getBalance());
				Transaction transaction4 = new Transaction(cuenta2, TransactionType.CREDIT, 900.00, "PedidosYa", LocalDateTime.now().plusDays(-3), cuenta2.getBalance());
				Transaction transaction5 = new Transaction(cuenta1, TransactionType.CREDIT, 10000.00, "PedidosYa", LocalDateTime.now().plusDays(-5), cuenta1.getBalance());
				Transaction transaction6 = new Transaction(cuenta1, TransactionType.DEBIT, -500.00, "Transaction", LocalDateTime.now().plusDays(-5), cuenta1.getBalance());
				Loan mortgage = new Loan("Mortgage",500000.00,20.00, List.of(12,24,36,48,60));
				Loan personal = new Loan("Personal",100000.00,15.00, List.of(6,12,24));
				Loan automotive = new Loan("Automotive",300000.00,17.00, List.of( 6,12,24,36));
				ClientLoan clientLoan1 = new ClientLoan(400000.00,60, client1,mortgage);
				ClientLoan clientLoan2 = new ClientLoan(50000.00,12, client1,personal);
				ClientLoan clientLoan3 = new ClientLoan(10000.00,24, client2,personal);
				ClientLoan clientLoan4 = new ClientLoan(20000.00,36, client2,automotive);


				Card card1 = new Card (client1,(client1.getFirstName() + " " + client1.getLastName()),"2415-2415-2415-2415",555,LocalDateTime.now().plusYears(-1),LocalDateTime.now(),CardColor.GOLD,CardType.DEBIT,true,cuenta1);
				Card card2 = new Card (client1,(client1.getFirstName() + " " + client1.getLastName()),"1614-1614-1614-1614",235,LocalDateTime.now().plusYears(5),LocalDateTime.now(),CardColor.TITANIUM,CardType.CREDIT,true,cuenta1);
				Card card3 = new Card (client2,(client2.getFirstName() + " " + client1.getLastName()),"3412-3412-3412-3412",535,LocalDateTime.now().plusYears(5),LocalDateTime.now(),CardColor.SILVER,CardType.CREDIT,true,cuenta2);
				//client1.addAccount(cuenta2);
//			asociando con el metodo addaccount
//en esta intancia pasa el cliente a estar guardado en el repositorio

				clientService.saveClient(client1);
				clientService.saveClient(client2);
				clientService.saveClient(client3);
				clientService.saveClient(admin);

				accountService.saveAccount(cuenta1);
				accountService.saveAccount(cuenta2);
				accountService.saveAccount(cuenta3);

				transactionService.saveTransaction(transaction1);
				transactionService.saveTransaction(transaction2);
				transactionService.saveTransaction(transaction3);
				transactionService.saveTransaction(transaction4);
				transactionService.saveTransaction(transaction5);
				transactionService.saveTransaction(transaction6);

				loanService.saveLoan(mortgage);
				loanService.saveLoan(personal);
				loanService.saveLoan(automotive);

				clientLoanService.saveClientLoan(clientLoan1);
				clientLoanService.saveClientLoan(clientLoan2);
				clientLoanService.saveClientLoan(clientLoan3);
				clientLoanService.saveClientLoan(clientLoan4);

				cardService.saveCard(card1);
				cardService.saveCard(card2);
				cardService.saveCard(card3);

			};
		}
}
