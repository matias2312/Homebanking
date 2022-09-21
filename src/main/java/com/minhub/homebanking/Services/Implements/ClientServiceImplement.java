package com.minhub.homebanking.Services.Implements;

import com.minhub.homebanking.Services.ClientService;
import com.minhub.homebanking.models.Client;
import com.minhub.homebanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImplement implements ClientService {
   @Autowired
   private ClientRepository clientRepository;

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }
    @Override
    public Client getClientById(Long Id){
       return clientRepository.findById(Id).get();
    }
    @Override
    public Client findByClientEmail(String email){
        return clientRepository.findByEmail(email);
    }
    @Override
    public void saveClient(Client client){
        clientRepository.save(client);
    }
}
