package com.minhub.homebanking.repository;

import com.minhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
}
//extend nuestro cliente repository va a heredar las  clases de nuestro jpa
//client es con lo que va a trabajr el repository, long es el tipo de dato
//interface