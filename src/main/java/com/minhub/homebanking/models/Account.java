package com.minhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//generamos la anotacion entity para crear la tabla en la base de datos
@Entity
//plantilla o molde para contruir nuestras cuentas
public class Account {//a nuestra entidad cuenta le vamos asignar propiedades
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")//se fija en la base de datos si hay un idpara no crear dos iguales
    private long id;
//pasamos a establecer una anotacion para asociar varias cuentas a un cliente
    @ManyToOne(fetch = FetchType.EAGER)//EAGER nos mantiene la inf actualizada
    @JoinColumn(name="client_id")
    private Client client;// esta es la prpopiedad que nos va ayudar asociar las cuentas con el cliente

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    private String number;
    private LocalDateTime creationDate;
    private Double balance;
    private Boolean active;
    private AccountType type;


//metodo constractur por defecto, depues de asigamos los parametros que necesito para poder crear el ojeto
    public Account() { }
    public Account(String number, LocalDateTime creationDate, Double balance, Client client,Boolean active ) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = client;
        this.active = active;
    }

    public Account(String number, LocalDateTime creationDate, Double balance,Client client, Boolean active, AccountType type) {
        this.client = client;
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.active = active;
        this.type = type;
    }





    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
   // @JsonIgnore
//    jackson tranforma el json que recibo del front para poder trabajar en java, evitar la recursividad
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
}
