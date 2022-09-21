package com.minhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String name;
    private Double maxAmount;

    private Double interest;


    @ElementCollection
    //se ultiliza para asociar no entidades( datos simples), una coleccion de datos simples
   // le permite simplificar el código cuando desea implementar una relación de uno a muchos con tipo dato simple o incrustado
    @Column(name="payments")
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy="loan", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan() {
    }

    public Loan(String name, Double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Loan(String name, Double maxAmount, Double interest, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.interest = interest;
        this.payments = payments;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    @JsonIgnore
    public List<Client> getClients (){
        return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(Collectors.toList());
    }


}
