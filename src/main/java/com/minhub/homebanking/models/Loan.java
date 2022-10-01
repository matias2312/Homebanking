package com.minhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @JsonIgnore // una forma no adecuada de evitar recursividad
    public List<Client> getClients (){
        return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(Collectors.toList());
    }


}
