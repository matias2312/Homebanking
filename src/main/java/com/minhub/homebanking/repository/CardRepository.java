package com.minhub.homebanking.repository;

import com.minhub.homebanking.models.Card;
import com.minhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
  Card findByNumber(String number);
}
