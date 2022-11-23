package com.minhub.homebanking.Services;

import com.minhub.homebanking.DTO.ClientDTO;
import com.minhub.homebanking.DTO.UserRequestDTO;
import com.minhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClientService {
     List<Client> getClients();
    void saveClient(Client client);
    ResponseEntity<Object> register(UserRequestDTO userRequestDTO);
    ClientDTO getClientId(Long id);
}
