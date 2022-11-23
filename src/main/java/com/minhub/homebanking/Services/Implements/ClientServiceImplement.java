package com.minhub.homebanking.Services.Implements;

import com.minhub.homebanking.DTO.AccountDTO;
import com.minhub.homebanking.DTO.ClientDTO;
import com.minhub.homebanking.DTO.UserRequestDTO;
import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.email.ValidatorEmail;
import com.minhub.homebanking.email.VerificationEmail;
import com.minhub.homebanking.exception.BankException;
import com.minhub.homebanking.exception.MessageErrorEnum;
import com.minhub.homebanking.models.Account;
import com.minhub.homebanking.models.AccountType;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repository.BankDAO;
import com.minhub.homebanking.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImplement implements ClientService {

   private final ClientRepository clientRepository;
   private final BankDAO bankDAO;
    private final VerificationEmail verificationEmail;
    private final ValidatorEmail validatorEmail;


    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public void saveClient(Client client){
        clientRepository.save(client);
    }

    @Override
    public ResponseEntity<Object> register(UserRequestDTO userRequestDTO) {
        Optional<Client> clientExists = Optional.ofNullable(bankDAO.findByClientEmail(userRequestDTO.getEmail()));
        if (clientExists.isPresent()) {
            throw new BankException(MessageErrorEnum.USER_EXISTS.getMessage());
        }
        if (!validatorEmail.test(userRequestDTO.getEmail())) {
            return new ResponseEntity<>("invalid mail", HttpStatus.FORBIDDEN);
        }
        Client user = bankDAO.createUser(userRequestDTO);
        AccountDTO accountDTO = AccountDTO.builder()
                .type(AccountType.SAVING)
                .build();
        bankDAO.createAccount(accountDTO,user);

        String mailSubject = "Welcome to Home Banking";
        String mailBody = "Thank you for registering, we are here to give you the best experience!";
        verificationEmail.sendEmail(user.getEmail(), mailSubject, mailBody);

        return new ResponseEntity<>("Client Created",HttpStatus.CREATED);
    }

    @Override
    public ClientDTO getClientId(Long id) {
        Optional<Client> clientExists = bankDAO.getClientById(id);
        Client userId = bankDAO.findByClientEmail(clientExists.get().getEmail());
        if(clientExists.isEmpty()) {
            throw new BankException("The requested user ID does not exist");
        }
        ClientDTO clientDTO = new ClientDTO(userId);
        return clientDTO;
    }

}
